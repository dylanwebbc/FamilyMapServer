package jsonModels;

/**
 * An Object containing an array of Locations for events in the family tree
 */
public class LocationData {

    private Location[] data;

    /**
     * Creates a new LocationData Object
     *
     * @param data an array of Location Objects
     */
    public LocationData(Location[] data) {
        setData(data);
    }

    public Location[] getData() {
        return data;
    }

    public void setData(Location[] data) {
        this.data = data;
    }
}
