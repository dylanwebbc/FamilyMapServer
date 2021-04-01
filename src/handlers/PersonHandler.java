package handlers;

import java.io.IOException;

import parsers.JsonSerializer;
import results.PeopleResult;
import results.PersonResult;
import services.PersonService;

import java.util.ArrayList;

/**
 * The handler for the person command
 */
public class PersonHandler extends AuthorizationHandler {

    /**
     * Processes a person HTTP request depending on the number of inputs
     *
     * @param requestData An array of URI arguments
     * @return the response Json data in the form of a string
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected String processRequest(ArrayList<String> requestData) throws IOException {
        String responseData = null;
        if (requestData.size() == 1) {
            PeopleResult peopleResult = PersonService.getPeople(requestData.get(0));
            responseData = JsonSerializer.serialize(peopleResult);
        }
        else if (requestData.size() == 2) {
            PersonResult personResult = PersonService.getPerson(requestData.get(0), requestData.get(1));
            responseData = JsonSerializer.serialize(personResult);
        }
        return responseData;
    }
}
