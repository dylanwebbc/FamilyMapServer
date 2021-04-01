package requests;

/**
 * The request for the register command
 */
public class RegisterRequest {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;

    /**
     * Creates a new RegisterRequest
     *
     * @param username the registering user's username
     * @param password the registering user's password
     * @param email the registering user's email
     * @param firstName the registering user's first name
     * @param lastName the registering user's last name
     * @param gender the registering user's gender
     */
    public RegisterRequest(String username, String password, String email,
                    String firstName, String lastName, String gender) {
        setUsername(username);
        setPassword(password);
        setEmail(email);
        setFirstName(firstName);
        setLastName(lastName);
        setGender(gender);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}