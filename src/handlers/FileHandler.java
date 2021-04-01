package handlers;

import java.io.*;
import java.net.*;
import java.nio.file.Files;

import com.sun.net.httpserver.*;

/**
 * The handler which serves up the webpage interface for the Server
 */
public class FileHandler implements HttpHandler {

    /**
     * Handles the HTTP request by serving up the HTML file in the web folder
     *
     * @param exchange An encapsulation of the HTTP request received
     * @throws IOException if an input/output error occurs
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;

        try {

            if (exchange.getRequestMethod().equalsIgnoreCase("get")) {

                String urlPath = exchange.getRequestURI().toString();

                if (urlPath == null || urlPath.equals("/")) {
                    urlPath = "/index.html";
                }

                File file = new File("web" + urlPath);

                if (file.exists()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                    file = new File("web/HTML/404.html");
                }

                //copies file from web folder and serves it up
                OutputStream responseBody = exchange.getResponseBody();
                Files.copy(file.toPath(), responseBody);
                responseBody.close();
                success = true;
            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

                exchange.getResponseBody().close();
            }
        }
        catch (IOException exception) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);

            exchange.getResponseBody().close();
            exception.printStackTrace();
        }
    }

}
