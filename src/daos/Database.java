package daos;

import java.sql.*;

/**
 * A data access object which interfaces with the FMS SQLite database
 */
public class Database {

    private Connection connection;

    /**
     * Opens a connection with the database
     *
     * @return a connection to the database
     * @throws SQLException if an SQL error occurs
     */
    public Connection openConnection() throws SQLException {

        try {
            final String url = "jdbc:sqlite:database/FMDB.sqlite";
            connection = DriverManager.getConnection(url);
            connection.setAutoCommit(false);
        }
        catch (SQLException exception) {
            throw new SQLException("Unable to open connection to database");
        }
        return connection;
    }

    /**
     * Retrieves the current connection to the database or opens a new one if no connection exists
     *
     * @return a connection to the database
     * @throws SQLException if an SQL error occurs
     */
    public Connection getConnection() throws SQLException {

        if (connection == null) {
            return openConnection();
        }
        else {
            return connection;
        }
    }

    /**
     * Closes the current connection to the database
     *
     * @param commit a boolean which indicates when to commit changes and when to rollback
     * @throws SQLException if an SQL error occurs
     */
    public void closeConnection(boolean commit) throws SQLException {

        try {
            if (commit) {
                connection.commit();
            }
            else {
                connection.rollback();
            }
            connection = null;
        }
        catch (SQLException exception) {
            throw new SQLException("Unable to close connection to database");
        }
    }

    /**
     * Clears all tables in the database
     *
     * @throws SQLException if an SQL error occurs
     */
    public void clearTables() throws SQLException {
        UserDao userDao = new UserDao(connection);
        userDao.clear();
        PersonDao personDao = new PersonDao(connection);
        personDao.clear();
        EventDao eventDao = new EventDao(connection);
        eventDao.clear();
        AuthTokenDao authTokenDao = new AuthTokenDao(connection);
        authTokenDao.clear();
    }

}
