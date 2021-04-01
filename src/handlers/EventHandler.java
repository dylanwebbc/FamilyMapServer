package handlers;

import java.io.IOException;

import parsers.JsonSerializer;
import results.EventResult;
import results.EventsResult;
import services.EventService;

import java.util.ArrayList;

/**
 * The handler for the event command
 */
public class EventHandler extends AuthorizationHandler {

    /**
     * Processes an event HTTP request depending on the number of inputs
     *
     * @param requestData An array of URI arguments
     * @return the response Json data in the form of a string
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected String processRequest(ArrayList<String> requestData) throws IOException {
        String responseData = null;
        if (requestData.size() == 1) {
            EventsResult eventsResult = EventService.getEvents(requestData.get(0));
            responseData = JsonSerializer.serialize(eventsResult);
        }
        else if (requestData.size() == 2) {
            EventResult eventResult = EventService.getEvent(requestData.get(0), requestData.get(1));
            responseData = JsonSerializer.serialize(eventResult);
        }
        return responseData;
    }
}
