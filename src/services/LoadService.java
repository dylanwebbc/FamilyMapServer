package services;

import daos.*;
import dbModels.*;
import requests.LoadRequest;
import results.Result;

import java.sql.*;
import java.util.ArrayList;

/**
 * An interface between the handler for the load command and the appropriate models and data access objects
 */
public class LoadService {

    /**
     * Clears all data from the database and loads new user, person and event data
     *
     * @param request the corresponding LoadRequest to the command
     * @return the corresponding Result to the LoadRequest
     */
    public static Result load(LoadRequest request) {
        Database database = new Database();
        ArrayList<User> users = request.getUsers();
        ArrayList<Person> people = request.getPersons();
        ArrayList<Event> events = request.getEvents();

        try {
            //open new connection, clear all data and insert given users, people and events
            Connection connection = database.openConnection();
            database.clearTables();
            UserDao userDao = new UserDao(connection);
            int numUsers = 0;
            for (User user : users) {
                userDao.insert(user);
                numUsers++;
            }
            PersonDao personDao = new PersonDao(connection);
            int numPeople = 0;
            for (Person person : people) {
                personDao.insert(person);
                numPeople++;
            }
            EventDao eventDao = new EventDao(connection);
            int numEvents = 0;
            for (Event event : events) {
                eventDao.insert(event);
                numEvents++;
            }
            database.closeConnection(true);

            String successMsg = "Successfully added " + numUsers + " users, " + numPeople + " persons, and "
                    + numEvents + " events to the database";
            return new Result(true, successMsg);
        }
        catch (SQLException exception1) {
            String errorMessage = exception1.getMessage();
            try {
                database.closeConnection(false);
            }
            catch (SQLException exception2) {
                errorMessage = exception2.getMessage();
            }
            return new Result(false, "Error: " + errorMessage);
        }
    }

}
