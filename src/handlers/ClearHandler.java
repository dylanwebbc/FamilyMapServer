package handlers;

import java.io.IOException;

import parsers.JsonSerializer;
import services.ClearService;
import results.Result;

/**
 * The handler for the clear command
 */
public class ClearHandler extends PostHandler {

    /**
     * Processes a clear HTTP request
     *
     * @param requestData A string of Json to be deserialized
     * @return the response Json data in the form of a string
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected String processRequest(String requestData) throws IOException {
        Result clearResult = ClearService.clear();
        return JsonSerializer.serialize(clearResult);
    }
}
