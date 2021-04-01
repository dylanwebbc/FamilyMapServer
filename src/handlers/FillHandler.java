package handlers;

import java.io.IOException;

import parsers.*;
import requests.FillRequest;
import results.Result;
import services.FillService;

import java.util.ArrayList;

/**
 * The handler for the fill command
 */
public class FillHandler extends PostHandler {

    /**
     * Processes a fill HTTP request
     *
     * @param requestData A string of Json to be deserialized
     * @return the response Json data in the form of a string
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected String processRequest(String requestData) throws IOException {
        ArrayList<String> requestArray = URIParser.parse(requestData);
        FillRequest fillRequest = null;
        if (requestArray.size() == 2) {
            fillRequest = new FillRequest(requestArray.get(1));
        }
        else if (requestArray.size() == 3){
            fillRequest = new FillRequest(requestArray.get(1), Integer.parseInt(requestArray.get(2)));
        }
        if (fillRequest != null) {
            Result fillResult = FillService.fill(fillRequest);
            return JsonSerializer.serialize(fillResult);
        }
        else {
            return null;
        }
    }
}
