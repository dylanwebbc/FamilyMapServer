package services;

import daos.*;
import dbModels.*;
import generators.EventGenerator;
import generators.IDGenerator;
import generators.PersonGenerator;
import requests.FillRequest;
import results.Result;

import java.io.IOException;
import java.sql.*;

/**
 * An interface between the handler for the fill command and the appropriate models and data access objects
 */
public class FillService {

    private static String username;
    private static int generations;

    /**
     * Fills the family tree data for a specified user
     *
     * @param request the corresponding FillRequest to the command
     * @return the corresponding Result to the FillRequest
     */
    public static Result fill(FillRequest request) {
        username = request.getUsername();
        generations = request.getGenerations();
        Database database = new Database();
        Person person;
        Event event;

        try {
            if (generations > 0) {
                //open new connection and find user
                Connection connection = database.openConnection();
                UserDao userDao = new UserDao(connection);
                User user = userDao.find(username);

                if (user != null) {
                    //if user exists, delete data already associated with user
                    PersonDao personDao = new PersonDao(connection);
                    EventDao eventDao = new EventDao(connection);
                    if (personDao.find(user.getPersonID()) != null) {
                        personDao.clear(username);
                        eventDao.clear(username);
                    }

                    //add user to people and add birth to events
                    person = new Person(user.getPersonID(), user.getUsername(), user.getFirstName(), user.getLastName(),
                            user.getGender(), null, null, null);
                    personDao.insert(person);
                    event = EventGenerator.generateEvent(user.getUsername(), user.getPersonID(),
                            "Birth", 1990);
                    eventDao.insert(event);

                    //recursively create family tree
                    addParents(connection, user.getPersonID(), 1);
                    database.closeConnection(true);

                    String successMsg = "Successfully added " + Math.round(Math.pow(2, generations + 1) - 1) + " persons" +
                            " and " + Math.round((Math.pow(2, generations + 1) - 1) * 3 - 2) + " events to the database";
                    return new Result(true, successMsg);
                }
                else {
                    database.closeConnection(false);
                    return new Result(false, "Error: Username does not exist");
                }
            }
            else {
                return new Result(false, "Error: Invalid number of generations");
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
            return new Result(false,"Error: " + errorMessage);
        }
        catch (IOException exception) {
            return new Result(false, "Error: " + exception.getMessage());
        }
    }

    private static void addParents(Connection connection, String personID, int currentGen)
            throws IOException, SQLException {

        //generate a random set of parents
        PersonDao personDao = new PersonDao(connection);
        Person mother = PersonGenerator.generatePerson(username, "f", null);
        Person father = PersonGenerator.generatePerson(username, "m", personDao.find(personID).getLastName());
        mother.setSpouseID(father.getPersonID());
        father.setSpouseID(mother.getPersonID());

        //insert parents in database and add IDs to child
        personDao.insert(mother);
        personDao.insert(father);
        personDao.updateParents(mother.getPersonID(), father.getPersonID(), personID);
        int birthYear = personDao.getEvents(personID).get(0).getYear() - 30;

        //add birth, marriage and death events for both parents
        addEvents(connection, mother.getPersonID(), father.getPersonID(), birthYear);

        //recursively add parents for both mother and father until generation limit reached
        if (currentGen < generations) {
            addParents(connection, mother.getPersonID(), currentGen + 1);
            addParents(connection, father.getPersonID(), currentGen + 1);
        }
    }

    private static void addEvents(Connection connection, String motherID, String fatherID, int birthYear)
            throws SQLException, IOException {
        EventDao eventDao = new EventDao(connection);

        eventDao.insert(EventGenerator.generateEvent(username, motherID, "Birth", birthYear));
        eventDao.insert(EventGenerator.generateEvent(username, fatherID, "Birth", birthYear));

        Event marriage = EventGenerator.generateEvent(username, motherID, "Marriage", birthYear + 30);
        eventDao.insert(marriage);
        marriage.setEventID(IDGenerator.generateID());
        marriage.setPersonID(fatherID);
        eventDao.insert(marriage);

        eventDao.insert(EventGenerator.generateEvent(username, motherID, "Death", birthYear + 80));
        eventDao.insert(EventGenerator.generateEvent(username, fatherID, "Death", birthYear + 80));
    }
}
