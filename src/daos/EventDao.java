package daos;

import java.sql.*;

import dbModels.Event;

/**
 * A data access object which interfaces with the events table in the FMS SQLite database
 */
public class EventDao {

    private final Connection connection;

    /**
     *  Creates a new EventDao with the given connection
     *
     * @param connection a connection to the database
     */
    public EventDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Inserts a new event into the events table
     *
     * @param event an Event object
     * @throws SQLException if an SQL error occurs
     */
    public void insert(Event event) throws SQLException {

        String sql = "insert into events (eventID, associatedUsername, personID, latitude, longitude, " +
                "country, city, eventType, year) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getAssociatedUsername());
            stmt.setString(3, event.getPersonID());
            stmt.setDouble(4, event.getLatitude());
            stmt.setDouble(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
        }
        catch (SQLException exception) {
            throw new SQLException("Error inserting event into the database");
        }
    }

    /**
     * Finds a specific event in the events table
     *
     * @param eventID an id for a specific event
     * @return a corresponding Event object if the specified event exists
     * @throws SQLException if an SQL error occurs
     */
    public Event find(String eventID) throws SQLException {

        Event event;
        ResultSet rs = null;
        String sql = "select * from events where eventID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("eventID"), rs.getString("associatedUsername"),
                        rs.getString("personID"), rs.getDouble("latitude"),
                        rs.getDouble("longitude"), rs.getString("country"),
                        rs.getString("city"), rs.getString("eventType"),
                        rs.getInt("year"));
                return event;
            }
        }
        catch (SQLException exception) {
            throw new SQLException("Error while locating specified event");
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
     * Clears the events table
     *
     * @throws SQLException if an SQL error occurs
     */
    public void clear() throws SQLException {

        String sql = "delete from events";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.execute();
        }
        catch (SQLException exception) {
            throw new SQLException("Failed to clear events table");
        }
    }

    /**
     * Removes only events related to a given user from the events table
     *
     * @param associatedUsername the username of the user for whom all related events will be removed
     * @throws SQLException if an SQL error occurs
     */
    public void clear(String associatedUsername) throws SQLException {

        String sql = "delete from events where associatedUsername = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, associatedUsername);
            stmt.execute();
        }
        catch (SQLException exception) {
            throw new SQLException("Failed to remove events related to given user from database");
        }
    }

}
