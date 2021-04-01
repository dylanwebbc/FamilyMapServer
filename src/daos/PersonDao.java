package daos;

import java.sql.*;
import java.util.ArrayList;

import dbModels.Person;
import dbModels.Event;

/**
 * A data access object which interfaces with the people table in the FMS SQLite database
 */
public class PersonDao {

    private final Connection connection;

    /**
     *  Creates a new PersonDao with the given connection
     *
     * @param connection a connection to the database
     */
    public PersonDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Inserts a new person into the people table
     *
     * @param person a Person object
     * @throws SQLException if an SQL error occurs
     */
    public void insert(Person person) throws SQLException {

        String sql = "insert into people (personID, associatedUsername, firstName, lastName, gender, " +
                "fatherID, motherID, spouseID) values (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();
        }
        catch (SQLException exception) {
            throw new SQLException("Error inserting person into the database");
        }
    }

    /**
     * Find a specific person in the people table
     *
     * @param personID an id for a specific person
     * @return a corresponding Person object if the specified person exists
     * @throws SQLException if an SQL error occurs
     */
    public Person find(String personID) throws SQLException {

        Person person;
        ResultSet rs = null;
        String sql = "select * from people where personID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("fatherID"),
                        rs.getString("motherID"), rs.getString("spouseID"));
                return person;
            }
        }
        catch (SQLException exception) {
            throw new SQLException("Error while locating specified person");
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
     * Clears the people table
     *
     * @throws SQLException if an SQL error occurs
     */
    public void clear() throws SQLException {

        String sql = "delete from people";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.execute();
        }
        catch (SQLException exception) {
            throw new SQLException("Failed to clear people table");
        }
    }

    /**
     * Removes only people related to a given user from the people table
     *
     * @param associatedUsername the username of the user for whom all related people will be removed
     * @throws SQLException if an SQL error occurs
     */
    public void clear(String associatedUsername) throws SQLException {

        String sql = "delete from people where associatedUsername = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, associatedUsername);
            stmt.execute();
        }
        catch (SQLException exception) {
            throw new SQLException("Failed to remove people related to given user from database");
        }
    }

    /**
     * Retrieves all the events corresponding to a specific person
     *
     * @param personID an id for a specific person
     * @return an array of all events corresponding to the specified person
     * @throws SQLException if an SQL error occurs
     */
    public ArrayList<Event> getEvents(String personID) throws SQLException {

        ArrayList<Event> events = new ArrayList<>();
        ResultSet rs = null;
        String sql = "select * from events where personID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Event event = new Event(rs.getString("eventID"), rs.getString("associatedUsername"),
                        rs.getString("personID"), rs.getDouble("latitude"),
                        rs.getDouble("longitude"), rs.getString("country"),
                        rs.getString("city"), rs.getString("eventType"),
                        rs.getInt("year"));
                events.add(event);
            }
            if (events.size() > 0) {
                return events;
            }
            else {
                throw new SQLException("noevents");
            }
        }
        catch (SQLException exception) {
            String errorMessage;
            if (exception.getMessage().equals("noevents")) {
                errorMessage = "No events found for specified personID";
            }
            else {
                errorMessage = "Error while locating events for specified personID";
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

    /**
     * adds parent IDs to a given person entry in the database
     *
     * @param motherID the id for the person's mother
     * @param fatherID the id for the person's father
     * @param personID the person's id
     * @throws SQLException if an SQL error occurs
     */
    public void updateParents(String motherID, String fatherID, String personID) throws SQLException {
        String sql = "update people set motherID = ?, fatherID = ? where personID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, motherID);
            stmt.setString(2, fatherID);
            stmt.setString(3, personID);
            stmt.execute();
        }
        catch (SQLException exception) {
            throw new SQLException("Failed to update parents of person " + personID);
        }
    }

}
