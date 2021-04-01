package handlers;

import java.io.IOException;

import parsers.JsonSerializer;
import requests.RegisterRequest;
import services.RegisterService;
import results.LoginResult;

/**
 * The handler for the register command
 */
public class RegisterHandler extends PostHandler {

    /**
     * Processes a register HTTP request
     *
     * @param requestData A string of Json to be deserialized
     * @return the response Json data in the form of a string
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected String processRequest(String requestData) throws IOException {
        RegisterRequest registerRequest = JsonSerializer.deserialize(requestData, RegisterRequest.class);
        LoginResult registerResult = RegisterService.register(registerRequest);
        return JsonSerializer.serialize(registerResult);
    }
}
