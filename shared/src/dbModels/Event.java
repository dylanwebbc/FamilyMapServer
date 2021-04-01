package dbModels;

/**
 * An important event in the life of a member of the family tree
 */
public class Event {

    private String eventID;
    private String associatedUsername;
    private String personID;
    private double latitude;
    private double longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;

    /**
     * Creates a new Event
     *
     * @param eventID a unique identifier for this event
     * @param associatedUsername the username of the person the event belongs to
     * @param personID the id of the person the event belongs to
     * @param latitude the latitude of the event's location
     * @param longitude the longitude of the event's location
     * @param country the country where the event occurred
     * @param city the city where the event occurred
     * @param eventType the eventType of the event (birth, baptism, christening, marriage, death, etc.)
     * @param year the year in which the event occurred
     */
    public Event(String eventID, String associatedUsername, String personID, double latitude, double longitude,
                 String country, String city, String eventType, int year) {
        setEventID(eventID);
        setAssociatedUsername(associatedUsername);
        setPersonID(personID);
        setLatitude(latitude);
        setLongitude(longitude);
        setCountry(country);
        setCity(city);
        setEventType(eventType);
        setYear(year);
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Event) {
            Event event = (Event) obj;
            return event.getEventID().equals(this.getEventID()) &&
                    event.getAssociatedUsername().equals(this.getAssociatedUsername()) &&
                    event.getPersonID().equals(this.getPersonID()) &&
                    event.getLatitude() == this.getLatitude() &&
                    event.getLongitude() == this.getLongitude() &&
                    event.getCountry().equals(this.getCountry()) &&
                    event.getCity().equals(this.getCity()) &&
                    event.getEventType().equals(this.getEventType()) &&
                    event.getYear() == this.getYear();
        }
        else {
            return false;
        }
    }
}