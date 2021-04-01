package handlers;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

/**
 * The parent class of handlers for requests which post data to the database
 * Children: register, login, load, fill, and clear handlers
 */
public class PostHandler extends Handler implements HttpHandler {

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
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                String uri = exchange.getRequestURI().toString();

                //deserialize request
                String requestData;
                if (uri.charAt(1) == 'f') {
                    requestData = uri;
                }
                else {
                    InputStream requestBody = exchange.getRequestBody();
                    requestData = readString(requestBody);
                }

                //process request and return result
                String responseData = processRequest(requestData);
                if (responseData != null) {
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
     * @param requestData A string of Json to be deserialized
     * @return the response Json data in the form of a string
     * @throws IOException if an input/output error occurs
     */
    protected String processRequest(String requestData) throws IOException {
        return null;
    }
}
