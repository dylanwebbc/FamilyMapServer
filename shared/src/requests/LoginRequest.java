package requests;

/**
 * The request for the login command
 */
public class LoginRequest {

    private String username;
    private String password;

    /**
     * Creates a new LoginRequest
     *
     * @param username the username of the user logging in
     * @param password the password of the user logging in
     */
    public LoginRequest(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
