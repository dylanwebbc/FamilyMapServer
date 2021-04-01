package services;

import daos.*;
import generators.*;
import dbModels.*;
import requests.*;
import results.*;

import java.sql.*;

/**
 * An interface between the handler for the register command and the appropriate models and data access objects
 */
public class RegisterService {

    /**
     * Registers a new user account, generates 4 generations of ancestor data for the new user, and logs them in
     * Returns an auth_token
     *
     * @param request the RegisterRequest to the corresponding command
     * @return the RegisterLoginResult to the corresponding RegisterRequest
     */
    public static LoginResult register(RegisterRequest request) {
        Database database = new Database();
        User user = new User(request.getUsername(), request.getPassword(), request.getEmail(), request.getFirstName(),
                request.getLastName(), request.getGender(), IDGenerator.generateID());

        try {
            //open new connection and insert user data in database
            Connection connection = database.openConnection();
            UserDao userDao = new UserDao(connection);
            userDao.insert(user);
            database.closeConnection(true);

            //generate a family tree with four generations for the user
            Result fillResult = FillService.fill(new FillRequest(user.getUsername(), 4));
            if (!fillResult.isSuccess()) {
                return new LoginResult(fillResult.getMessage());
            }

            //log the new user in and return login result
            return LoginService.login(new LoginRequest(user.getUsername(), user.getPassword()));
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
