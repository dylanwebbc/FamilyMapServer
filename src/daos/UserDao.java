package daos;

import java.sql.*;
import java.util.ArrayList;

import dbModels.User;
import dbModels.Person;

/**
 * A data access object which interfaces with the users table in the FMS SQLite database
 */
public class UserDao {

    private final Connection connection;

    /**
     * Creates a new UserDao with the given connection
     *
     * @param connection a connection to the database
     */
    public UserDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Inserts a new user into the users table
     *
     * @param user a User object
     * @throws SQLException if an SQL error occurs
     */
    public void insert(User user) throws SQLException {

        String sql = "insert into users (username, password, email, firstName, lastName, gender, personID) " +
                "values (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());

            stmt.executeUpdate();
        }
        catch (SQLException exception) {
            throw new SQLException("Error inserting user into the database");
        }
    }

    /**
     * Finds a specific user in the users table
     *
     * @param username a username for a specific user
     * @return a corresponding User object if the specified user exists
     * @throws SQLException if an SQL error occurs
     */
    public User find(String username) throws SQLException {

        User user;
        ResultSet rs = null;
        String sql = "select * from users where username = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("username"), rs.getString("password"),
                        rs.getString("email"), rs.getString("firstName"),
                        rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("personID"));
                return user;
            }
        }
        catch (SQLException exception) {
            throw new SQLException("Error while locating specified user");
        }
        finally {
            if (rs != null) {
                try {
                    rs.close();
                }
                catch (SQLException exception) {
                    throw new SQLException("Error while closing result set");
                }
            }
        }
        return null;
    }

    /**
     * Clears the users table
     *
     * @throws SQLException if an SQL error occurs
     */
    public void clear() throws SQLException {

        String sql = "delete from users";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.execute();
        }
        catch (SQLException exception) {
            throw new SQLException("Failed to clear users table");
        }
    }

    /**
     * Retrieves the family tree corresponding to a specific user
     *
     * @param associatedUsername a username for a specific user
     * @return an array of all people in the corresponding family tree
     * @throws SQLException if an SQL error occurs
     */
    public ArrayList<Person> getFamily(String associatedUsername) throws SQLException {
        ArrayList<Person> people = new ArrayList<>();
        ResultSet rs = null;
        String sql = "select * from people where associatedUsername = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, associatedUsername);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Person person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("fatherID"),
                        rs.getString("motherID"), rs.getString("spouseID"));
                people.add(person);
            }
            if (people.size() > 0) {
                return people;
            }
            throw new SQLException("norelatives");
        }
        catch (SQLException exception) {
            String errorMessage;
            if (exception.getMessage().equals("norelatives")) {
                errorMessage = "No relatives found for specified username";
            }
            else {
                errorMessage = "Error while locating relatives for specified personID";
            }
            throw new SQLException(errorMessage);
        }
        finally {
            if (rs != null) {
                try {
                    rs.close();
                }
                catch (SQLException exception) {
                    throw new SQLException("Error while closing result set");
                }
            }
        }
    }

}
