package services;

import daos.*;
import dbModels.*;
import generators.IDGenerator;
import requests.LoginRequest;
import results.LoginResult;

import java.sql.*;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * An interface between the handler for the login command and the appropriate models and data access objects
 */
public class LoginService {

    /**
     * Logs a user into the server
     * Returns an auth_token
     *
     * @param request the LoginRequest to the corresponding command
     * @return the RegisterLoginResult to the corresponding LoginRequest
     */
    public static LoginResult login(LoginRequest request) {
        Database database = new Database();
        AuthToken authToken;

        try {
            //open new connection and find user
            Connection connection = database.openConnection();
            UserDao userDao = new UserDao(connection);
            User user = userDao.find(request.getUsername());

            //if user exists, create a new authtoken for this login
            if (user != null) {
                if (user.getPassword().equals(request.getPassword())) {
                    authToken = new AuthToken(IDGenerator.generateID(), user.getPersonID(),
                            new Timestamp(System.currentTimeMillis()));
                    AuthTokenDao authTokenDao = new AuthTokenDao(connection);
                    authTokenDao.insert(authToken);
                }
                else {
                    database.closeConnection(false);
                    return new LoginResult("Error: Incorrect password");
                }
            }
            else {
                database.closeConnection(false);
                return new LoginResult("Error: Username does not exist");
            }

            database.closeConnection(true);
            return new LoginResult(authToken.getAuthtoken(), user.getUsername(), user.getPersonID());
        }
        catch (SQLException exception1) {
            String errorMessage = exception1.getMessage();
            try {
                database.closeConnection(false);
            }
            catch (SQLException exception2) {
                errorMessage = exception2.getMessage();
            }
            return new LoginResult("Error: " + errorMessage);
        }
    }

}
