package results;

/**
 * The response to the register and login requests
 */
public class LoginResult extends Result {

    private String authtoken;
    private String username;
    private String personID;

    /**
     * Creates a new RegisterLoginResult when the corresponding request succeeds
     *
     * @param authtoken an authorization token for the queried user
     * @param username the corresponding username of the same user
     * @param personID the corresponding person_id of the same user
     */
    public LoginResult(String authtoken, String username, String personID) {
        super(true, null);
        setAuthtoken(authtoken);
        setUsername(username);
        setPersonID(personID);
    }

    /**
     * Creates a new RegisterLoginResult when the corresponding request fails
     *
     * @param message a description of the error which occurred
     */
    public LoginResult(String message) {
        super(false, message);
        setAuthtoken(null);
        setUsername(null);
        setPersonID(null);
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

}