package jsonModels;

/**
 * The Location where a family tree Event occurred
 */
public class Location {

    private double latitude;
    private double longitude;
    private String country;
    private String city;

    /**
     * Creates a new Location for an event
     *
     * @param latitude the latitude of the event's location
     * @param longitude the longitude of the event's location
     * @param country the country where the event occurred
     * @param city the city where the event occurred
     */
    Location(double latitude, double longitude, String country, String city) {
        setLatitude(latitude);
        setLongitude(longitude);
        setCountry(country);
        setCity(city);
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
}
