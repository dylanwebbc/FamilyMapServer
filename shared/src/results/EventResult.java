package results;

import dbModels.Event;

/**
 * The response to the person request when event_id is provided
 */
public class EventResult extends Result {

    private String associatedUsername;
    private String eventID;
    private String personID;
    private double latitude;
    private double longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;

    /**
     * Creates a new EventResult when the corresponding request succeeds
     *
     * @param event an Event object for the queried event
     */
    public EventResult(Event event) {
        super(true, null);
        setAssociatedUsername(event.getAssociatedUsername());
        setEventID(event.getEventID());
        setPersonID(event.getPersonID());
        setLatitude(event.getLatitude());
        setLongitude(event.getLongitude());
        setCountry(event.getCountry());
        setCity(event.getCity());
        setEventType(event.getEventType());
        setYear(event.getYear());
    }

    /**
     * Creates a new EventResult when the corresponding request fails
     *
     * @param message a description of the error which occurred
     */
    public EventResult(String message) {
        super(false, message);
        setAssociatedUsername(null);
        setEventID(null);
        setPersonID(null);
        setLatitude(0);
        setLongitude(0);
        setCountry(null);
        setCity(null);
        setEventType(null);
        setYear(0);
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
