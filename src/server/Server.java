package server;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

import handlers.*;

/**
 * The family map server allows access to the database through the client and the web
 */
public class Server {

    private static final int MAX_WAITING_CONNECTIONS = 12;

    /**
     * Runs the server locally allowing access through a web browser
     *
     * @param portNumber the port to which the server will connect
     */
    private void run(String portNumber) {
        HttpServer server;

        try {
            server = HttpServer.create(new InetSocketAddress(Integer.parseInt(portNumber)), MAX_WAITING_CONNECTIONS);
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }

        server.setExecutor(null);

        server.createContext("/", new FileHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/fill", new FillHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/person", new PersonHandler());
        server.createContext("/event", new EventHandler());

        System.out.println("Starting Server...");
        server.start();
    }

    /**
     * The main method for the family map server
     *
     * @param args the arguments for main--port number should be the first argument
     */
    public static void main(String[] args) {
        String portNumber = args[0];
        new Server().run(portNumber);
    }

}