package generators;

import jsonModels.*;
import parsers.*;
import dbModels.Event;

import java.io.IOException;
import java.util.Random;

/**
 * Generates Events for the family tree using the Location file in the json folder
 */
public class EventGenerator {

    /**
     * Generates an Event with a random location using the given information
     *
     * @param username the username of the user to whose family tree this Event will belong
     * @param personID the ID of the person to whom this Event will belong
     * @param eventType the type of Event to be generated
     * @param eventYear the year of the Event to be generated
     * @return an Event Object
     * @throws IOException if an input/output error occurs
     */
    public static Event generateEvent(String username, String personID, String eventType, int eventYear)
            throws IOException {

        try {
            String jsonLocations = FileParser.parse("json/locations.json").toString();
            LocationData locationData = JsonSerializer.deserialize(jsonLocations, LocationData.class);
            int rnd = new Random().nextInt(locationData.getData().length);
            Location location = locationData.getData()[rnd];
            return new Event(IDGenerator.generateID(), username, personID, location.getLatitude(),
                    location.getLongitude(), location.getCountry(), location.getCity(), eventType,
                    getRandomYear(eventYear));
        }
        catch (IOException exception) {
            throw new IOException("Error in parsing json locations file");
        }
    }

    /**
     * Randomly fluctuates a given year by up to 10 for a more random appearance when the fill command is used
     *
     * @param baseYear the year to be modified
     * @return the baseYear with up to 10 years added
     */
    private static int getRandomYear(int baseYear) {
        int rnd = new Random().nextInt(10);
        return rnd + baseYear;
    }

}
