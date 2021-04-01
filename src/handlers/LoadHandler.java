package handlers;

import java.io.IOException;

import parsers.JsonSerializer;
import requests.LoadRequest;
import results.Result;
import services.LoadService;

/**
 * The handler for the load command
 */
public class LoadHandler extends PostHandler {

    /**
     * Processes a load HTTP request
     *
     * @param requestData A string of Json to be deserialized
     * @return the response Json data in the form of a string
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected String processRequest(String requestData) throws IOException {
        LoadRequest loadRequest = JsonSerializer.deserialize(requestData, LoadRequest.class);
        Result loadResult = LoadService.load(loadRequest);
        return JsonSerializer.serialize(loadResult);
    }
}
