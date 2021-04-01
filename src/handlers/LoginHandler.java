package handlers;

import parsers.JsonSerializer;
import requests.LoginRequest;
import results.LoginResult;
import services.LoginService;

import java.io.IOException;

/**
 * The handler for the login command
 */
public class LoginHandler extends PostHandler {

    /**
     * Processes a login HTTP request
     *
     * @param requestData A string of Json to be deserialized
     * @return the response Json data in the form of a string
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected String processRequest(String requestData) throws IOException {
        LoginRequest loginRequest = JsonSerializer.deserialize(requestData, LoginRequest.class);
        LoginResult loginResult = LoginService.login(loginRequest);
        return JsonSerializer.serialize(loginResult);
    }
}