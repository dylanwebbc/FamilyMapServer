package services;

import daos.*;
import dbModels.*;
import results.PersonResult;
import results.PeopleResult;

import java.sql.*;
import java.util.ArrayList;

/**
 * An interface between the handler for the person command and the appropriate models and data access objects
 */
public class PersonService {

    /**
     * Retrieves a specified person
     *
     * @param personID the id of the person to be retrieved
     * @param authtoken the authorization token of the user making the request
     * @return the PersonResult for the corresponding person
     */
    public static PersonResult getPerson(String personID, String authtoken) {
        Database database = new Database();

        try {
            //open new connection and find person in database
            Connection connection = database.openConnection();
            PersonDao personDao = new PersonDao(connection);
            Person person = personDao.find(personID);

            if (person != null) {
                //if person exists, find authtoken in database
                String associatedUsername1 = person.getAssociatedUsername();
                AuthTokenDao authTokenDao = new AuthTokenDao(connection);
                AuthToken authToken = authTokenDao.find(authtoken);

                if (authToken != null) {
                    //if authtoken exists, verify that person belongs to user with specified authtoken
                    String userID = authToken.getPersonID();
                    String associatedUsername2 = personDao.find(userID).getAssociatedUsername();
                    database.closeConnection(false);

                    if (associatedUsername1.equals(associatedUsername2)) {
                        return new PersonResult(person);
                    }
                    else {
                        return new PersonResult("Error: Requested person doesn't belong to user's family tree");
                    }
                }
                else {
                    database.closeConnection(false);
                    return new PersonResult("Error: Invalid authtoken");
                }
            }
            else {
                database.closeConnection(false);
                return new PersonResult("Error: Invalid personID");
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
            return new PersonResult("Error: " + errorMessage);
        }
    }

    /**
     * Retrieves every member of the user's family tree
     *
     * @param authtoken the authorization token of the user making the request
     * @return the PeopleResult for the corresponding user
     */
    public static PeopleResult getPeople(String authtoken) {
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

                database.closeConnection(false);
                return new PeopleResult(people);
            }
            else {
                database.closeConnection(false);
                return new PeopleResult("Error: Invalid authtoken");
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
            return new PeopleResult("Error: " + errorMessage);
        }
    }

}
