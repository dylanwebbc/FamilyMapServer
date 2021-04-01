package results;

import dbModels.Event;

import java.util.ArrayList;

/**
 * The response to the person request when no event_id is provided
 */
public class EventsResult extends Result {

    private ArrayList<Event> data;

    /**
     * Creates a new EventsResult when the corresponding request succeeds
     *
     * @param data an array containing the events of all members of the current user's family tree
     */
    public EventsResult(ArrayList<Event> data) {
        super(true, null);
        setData(data);
    }

    /**
     * Creates a new EventsResult when the corresponding request fails
     *
     * @param message a description of the error which occurred
     */
    public EventsResult(String message) {
        super(false, message);
        setData(null);
    }

    public ArrayList<Event> getData() {
        return data;
    }

    public void setData(ArrayList<Event> data) {
        this.data = data;
    }
}
