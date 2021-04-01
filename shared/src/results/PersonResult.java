package results;

import dbModels.Person;

/**
 * The response to the person request when person_id is provided
 */
public class PersonResult extends Result {

    private String associatedUsername;
    private String personID;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouseID;

    /**
     * Creates a new PersonResult when the corresponding request succeeds
     *
     * @param person a Person object for the queried person
     */
    public PersonResult(Person person) {
        super(true, null);
        setAssociatedUsername(person.getAssociatedUsername());
        setPersonID(person.getPersonID());
        setFirstName(person.getFirstName());
        setLastName(person.getLastName());
        setGender(person.getGender());
        setFatherID(person.getFatherID());
        setMotherID(person.getMotherID());
        setSpouseID(person.getSpouseID());
    }

    /**
     * Creates a new PersonResult when the corresponding request fails
     *
     * @param message a description of the error which occurred
     */
    public PersonResult(String message) {
        super(false, message);
        setAssociatedUsername(null);
        setPersonID(null);
        setFirstName(null);
        setLastName(null);
        setGender(null);
        setFatherID(null);
        setMotherID(null);
        setSpouseID(null);
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
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

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }
}
