package parsers;

import com.google.gson.Gson;

import java.io.IOException;

/**
 * Serializer for converting between Json and Objects
 * Accessed by classes in the handlers and generators packages
 */
public class JsonSerializer {

    /**
     * Converts an Object to Json
     *
     * @param object the object to be converted
     * @return a Json representation of the Object
     * @throws IOException if an input/output error occurs
     */
    public static String serialize(Object object) throws IOException {
        return (new Gson()).toJson(object);
    }

    /**
     * Converts Json to an Object
     *
     * @param json the Json to be converted
     * @param returnType the resulting Object type
     * @return an Object representation of the Json
     * @throws IOException if an input/output error occurs
     */
    public static <T> T deserialize(String json, Class<T> returnType) throws IOException {
        return (new Gson()).fromJson(json, returnType);
    }

}
