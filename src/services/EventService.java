package services;

import daos.*;
import dbModels.*;
import results.EventResult;
import results.EventsResult;

import java.sql.*;
import java.util.ArrayList;

/**
 * An interface between the handler for the event command and the appropriate models and data access objects
 */
public class EventService {

    /**
     * Retrieves a specified event
     *
     * @param eventID the id of the event to be retrieved
     * @param authtoken the authorization token of the user making the request
     * @return the EventResult for the corresponding event
     */
    public static EventResult getEvent(String eventID, String authtoken) {
        Database database = new Database();

        try {
            //open new connection and find event in database
            Connection connection = database.openConnection();
            EventDao eventDao = new EventDao(connection);
            Event event = eventDao.find(eventID);

            if (event != null) {
                //if event exists, find authtoken in database
                String associatedUsername1 = event.getAssociatedUsername();
                AuthTokenDao authTokenDao = new AuthTokenDao(connection);
                AuthToken authToken = authTokenDao.find(authtoken);

                if (authToken != null) {
                    //if authtoken exists, verify that event belongs to user with specified authtoken
                    String personID = authToken.getPersonID();
                    PersonDao personDao = new PersonDao(connection);
                    String associatedUsername2 = personDao.find(personID).getAssociatedUsername();
                    database.closeConnection(false);

                    if (associatedUsername1.equals(associatedUsername2)) {
                        return new EventResult(event);
                    }
                    else {
                        return new EventResult("Error: Requested event doesn't belong to user's family tree");
                    }
                }
                else {
                    database.closeConnection(false);
                    return new EventResult("Error: Invalid authtoken");
                }
            }
            else {
                database.closeConnection(false);
                return new EventResult("Error: Invalid eventID");
            }

        }
        catch (SQLException exception1) {
            String errorMessage = exception1.getMessage();
            try {
                database.closeConnection(false);
            }
            catch (SQLException exception2) {
                errorMessage = exception2.getMessage();
            }
            return new EventResult("Error: " + errorMessage);
        }
    }

    /**
     * Retrieves all events for every member of the user's family tree
     *
     * @param authtoken the authorization token of the user making the request
     * @return the EventsResult for the corresponding user
     */
    public static EventsResult getEvents(String authtoken) {
        Database database = new Database();

        try {
            //open new connection and specified authtoken
            Connection connection = database.openConnection();
            AuthTokenDao authTokenDao = new AuthTokenDao(connection);
            AuthToken authToken = authTokenDao.find(authtoken);

            if (authToken != null) {
                // if authtoken exists, find associated user and return family tree
                String personID = authToken.getPersonID();
                PersonDao personDao = new PersonDao(connection);
                String username = personDao.find(personID).getAssociatedUsername();
                UserDao userDao = new UserDao(connection);
                ArrayList<Person> people = userDao.getFamily(username);

                //loop through family tree and return all associated events
                ArrayList<Event> events = new ArrayList<>();
                for (Person person : people) {
                    events.addAll(personDao.getEvents(person.getPersonID()));
                }
                database.closeConnection(false);
                return new EventsResult(events);
            }
            else {
                database.closeConnection(false);
                return new EventsResult("Error: Invalid authtoken");
            }

        }
        catch (SQLException exception1) {
            String errorMessage = exception1.getMessage();
            try {
                database.closeConnection(false);
            }
            catch (SQLException exception2) {
                errorMessage = exception2.getMessage();
            }
            return new EventsResult("Error: " + errorMessage);
        }
    }
}
