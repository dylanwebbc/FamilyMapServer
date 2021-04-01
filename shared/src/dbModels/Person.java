package dbModels;

/**
 * A person who is a member of the family tree
 */
public class Person {

    private String personID;
    private String associatedUsername;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouseID;

    /**
     * Creates a new Person
     *
     * @param personID a unique identifier for this person
     * @param associatedUsername the username of the user to whose family tree this person belongs
     * @param firstName the person's first name
     * @param lastName the person's last name
     * @param gender the person's gender (either 'f' or 'm')
     * @param fatherID the person_id of the person's father (possibly null)
     * @param motherID the person_id of the person's mother (possibly null)
     * @param spouseID the person_id of the person's spouse (possibly null)
     */
    public Person(String personID, String associatedUsername, String firstName, String lastName, String gender,
                  String fatherID, String motherID, String spouseID) {
        setPersonID(personID);
        setAssociatedUsername(associatedUsername);
        setFirstName(firstName);
        setLastName(lastName);
        setGender(gender);
        setFatherID(fatherID);
        setMotherID(motherID);
        setSpouseID(spouseID);
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Person) {
            Person person = (Person) obj;
            boolean equal = true;
            if (!person.getPersonID().equals(this.getPersonID()) ||
                    !person.getAssociatedUsername().equals(this.getAssociatedUsername()) ||
                    !person.getFirstName().equals(this.getFirstName()) ||
                    !person.getLastName().equals(this.getLastName()) ||
                    !person.getGender().equals(this.getGender())) {
                equal = false;
            }
            if ((person.getFatherID() != null && this.getFatherID() == null) ||
                    (person.getFatherID() == null && this.getFatherID() != null) ||
                    (person.getMotherID() != null && this.getMotherID() == null) ||
                    (person.getMotherID() == null && this.getMotherID() != null) ||
                    (person.getSpouseID() != null && this.getSpouseID() == null) ||
                    (person.getSpouseID() == null && this.getSpouseID() != null)) {
                equal = false;
            }
            if (person.getFatherID() != null) {
                if (!person.getFatherID().equals(this.getFatherID())) {
                    equal = false;
                }
            }
            if (person.getMotherID() != null) {
                if (!person.getMotherID().equals(this.getMotherID())) {
                    equal = false;
                }
            }
            if (person.getSpouseID() != null) {
                if (!person.getSpouseID().equals(this.getSpouseID())) {
                    equal = false;
                }
            }
            return equal;
        }
        else {
            return false;
        }
    }
}
