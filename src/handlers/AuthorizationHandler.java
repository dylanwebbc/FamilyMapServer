package handlers;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

import parsers.URIParser;
import java.util.ArrayList;

/**
 * The parent class of handlers for requests requiring authorization in the form of an authtoken
 * Children: event and person handlers
 */
public class AuthorizationHandler extends Handler implements HttpHandler {

    /**
     * Handles the HTTP request by deserializing it and passing the request to the appropriate service
     * It then writes the response to the return body as Json
     *
     * @param exchange An encapsulation of the HTTP request received
     * @throws IOException if an input/output error occurs
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;

        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("get")) {

                if (exchange.getRequestHeaders().containsKey("Authorization")) {
                    String authtoken = exchange.getRequestHeaders().getFirst("Authorization");

                    //deserialize and process request
                    URI requestBody = exchange.getRequestURI();
                    ArrayList<String> requestData = URIParser.parse(requestBody.toString());
                    requestData.remove(0);
                    requestData.add(authtoken);
                    String responseData = processRequest(requestData);

                    if (responseData != null) {
                        //write result to response body
                        if (responseData.contains("true")) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        }
                        else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        }
                        OutputStream responseBody = exchange.getResponseBody();
                        writeString(responseData, responseBody);
                        responseBody.close();

                        success = true;
                    }
                }
            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                String responseData = "{\"message\":\"Error: Invalid HTTP\",\"success\":false}";
                OutputStream responseBody = exchange.getResponseBody();
                writeString(responseData, responseBody);
                responseBody.close();
            }
        }
        catch (IOException exception) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);

            exchange.getResponseBody().close();
            System.out.println("Invalid input/output");
        }
    }

    /**
     * Processes an HTTP request
     * The exact code is specified in child classes
     *
     * @param requestData An array of URI arguments
     * @return the response Json data in the form of a string
     * @throws IOException if an input/output error occurs
     */
    protected String processRequest(ArrayList<String> requestData) throws IOException {
        return null;
    }
}
