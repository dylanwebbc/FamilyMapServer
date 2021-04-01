package dbModels;

import java.sql.Timestamp;

/**
 * A unique authorization token created each time a user logs in
 */
public class AuthToken {

    private String authtoken;
    private String personID;
    private Timestamp timestamp;

    /**
     * Creates a new AuthToken
     *
     * @param authtoken an authorization token for the corresponding user
     * @param personID the id of the corresponding user
     * @param timestamp the date and time of the user's login
     */
    public AuthToken(String authtoken, String personID, Timestamp timestamp) {
        setAuthtoken(authtoken);
        setPersonID(personID);
        setTimestamp(timestamp);
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof AuthToken) {
            AuthToken authToken = (AuthToken) obj;
            return authToken.getAuthtoken().equals(this.getAuthtoken()) &&
                    authToken.getPersonID().equals(this.getPersonID()) &&
                    authToken.getTimestamp().equals(this.getTimestamp());
        }
        else {
            return false;
        }
    }
}
