package generators;

import jsonModels.NameData;
import parsers.JsonSerializer;
import dbModels.Person;
import parsers.FileParser;

import java.io.IOException;
import java.util.Random;

/**
 * Generates Person Objects for the family tree using the fnames, mnames and snames files in the json folder
 */
public class PersonGenerator {

    /**
     * Generates a Person with a random name using the given information
     *
     * @param username the username of the user to whose family tree this Person will belong
     * @param gender the gender of the Person being generated
     * @return a new Person object
     * @throws IOException if an input or output error occurs
     */
    public static Person generatePerson(String username, String gender, String lastName) throws IOException {
        String pathname;
        if (gender.equals("m")) {
            pathname = "json/mnames.json";
        }
        else if (gender.equals("f")) {
            pathname = "json/fnames.json";
        }
        else {
            throw new IOException("Incorrect gender");
        }
        String firstName = getRandomName(pathname);
        if (lastName == null) {
            lastName = getRandomName("json/snames.json");
        }
        String personID = IDGenerator.generateID();
        return new Person(personID, username, firstName, lastName, gender, null, null, null);
    }

    /**
     * Gets a random name from the specified Json file
     *
     * @param pathname the filepath of the json names file to draw from
     * @return a string selected at random from the file
     * @throws IOException if an input/output error occurs
     */
    private static String getRandomName(String pathname) throws IOException {
        try {
            String jsonNames = FileParser.parse(pathname).toString();
            NameData nameData = JsonSerializer.deserialize(jsonNames, NameData.class);
            int rnd = new Random().nextInt(nameData.getData().length);
            return nameData.getData()[rnd];
        }
        catch (IOException exception) {
            throw new IOException("Error in parsing json firstnames file");
        }
    }
}

