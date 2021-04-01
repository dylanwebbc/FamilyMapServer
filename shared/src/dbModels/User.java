package dbModels;

/**
 * A user of the family tree program who acts as the root of the tree
 */
public class User {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String personID;

    /**
     * Creates a new User
     *
     * @param username the user's unique username
     * @param password the user's unique password
     * @param email the user's email address
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param gender the user's gender (either 'f' or 'm')
     * @param personID the id of this user's generated Person object
     */
    public User(String username, String password, String email,
                String firstName, String lastName, String gender, String personID) {
        setUsername(username);
        setPassword(password);
        setEmail(email);
        setFirstName(firstName);
        setLastName(lastName);
        setGender(gender);
        setPersonID(personID);
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

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof User) {
            User user = (User) obj;
            return user.getUsername().equals(this.getUsername()) &&
                    user.getPassword().equals(this.getPassword()) &&
                    user.getEmail().equals(this.getEmail()) &&
                    user.getFirstName().equals(this.getFirstName()) &&
                    user.getLastName().equals(this.getLastName()) &&
                    user.getGender().equals(this.getGender()) &&
                    user.getPersonID().equals(this.getPersonID());
        }
        else {
            return false;
        }
    }
}
