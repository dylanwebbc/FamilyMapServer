package results;

import dbModels.Person;

import java.util.ArrayList;

/**
 * The response to the person request when no person_id is provided
 */
public class PeopleResult extends Result {

    private ArrayList<Person> data;

    /**
     * Creates a new PeopleResult when the corresponding request succeeds
     *
     * @param data an array containing the family tree of the current user
     */
    public PeopleResult(ArrayList<Person> data) {
        super(true, null);
        setData(data);
    }

    /**
     * Creates a new PeopleResult when the corresponding request fails
     *
     * @param message a description of the error which occurred
     */
    public PeopleResult(String message) {
        super(false, message);
        setData(null);
    }

    public ArrayList<Person> getData() {
        return data;
    }

    public void setData(ArrayList<Person> data) {
        this.data = data;
    }
}
