package services;

import results.Result;
import daos.Database;

import java.sql.SQLException;

/**
 * An interface between the handler for the clear command and the appropriate models and data access objects
 */
public class ClearService {

    /**
     * Clears all data from the database
     *
     * @return a Result object
     */
    public static Result clear() {
        Database database = new Database();
        try {
            database.openConnection();
            database.clearTables();
            database.closeConnection(true);
            return new Result(true, "Clear succeeded");
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
