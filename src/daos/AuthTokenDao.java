package daos;

import java.sql.*;

import dbModels.AuthToken;

/**
 * A data access object which interfaces with the authtokens table in the FMS SQLite database
 */
public class AuthTokenDao {

    private final Connection connection;

    /**
     * Creates a new AuthTokenDao with the given connection
     *
     * @param connection a connection to the database
     */
    public AuthTokenDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Inserts a new authorization token into the authtokens table
     *
     * @param authToken an AuthToken object
     * @throws SQLException if an SQL error occurs
     */
    public void insert(AuthToken authToken) throws SQLException {

        String sql = "insert into authtokens (authtoken, personID, timestamp) values (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, authToken.getAuthtoken());
            stmt.setString(2, authToken.getPersonID());
            stmt.setTimestamp(3, authToken.getTimestamp());

            stmt.executeUpdate();
        }
        catch (SQLException exception) {
            throw new SQLException("Error inserting authorization token into the database");
        }
    }

    /**
     * Finds a specific authorization token in the authtokens table
     *
     * @param authtoken an authorization token for a specific user
     * @return a corresponding AuthToken object if the specified authorization token exists
     * @throws SQLException if an SQL error occurs
     */
    public AuthToken find(String authtoken) throws SQLException {

        AuthToken authToken;
        ResultSet rs = null;
        String sql = "select * from authtokens where authtoken = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, authtoken);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authToken = new AuthToken(rs.getString("authtoken"), rs.getString("personID"),
                        rs.getTimestamp("timestamp"));
                return authToken;
            }
        }
        catch (SQLException exception) {
            throw new SQLException("Error while locating specified authorization token");
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
     * Clears the authtokens table
     *
     * @throws SQLException if an SQL error occurs
     */
    public void clear() throws SQLException {

        String sql = "delete from authtokens";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.execute();
        }
        catch (SQLException exception) {
            throw new SQLException("Failed to clear authtokens table");
        }
    }

}