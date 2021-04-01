package requests;

import dbModels.*;

import java.util.ArrayList;

/**
 * The request for the load command
 */
public class LoadRequest {

    private ArrayList<User> users;
    private ArrayList<Person> persons;
    private ArrayList<Event> events;

    /**
     * Creates a new LoadRequest
     *
     * @param users an array of users to be loaded into the cleared database
     * @param persons an array of people to be loaded into the cleared database
     * @param events an array of events to be loaded into the cleared database
     */
    public LoadRequest(ArrayList<User> users, ArrayList<Person> persons, ArrayList<Event> events) {
        setUsers(users);
        setPersons(persons);
        setEvents(events);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}
