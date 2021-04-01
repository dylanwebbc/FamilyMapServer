package parsers;

import java.util.ArrayList;

/**
 * Parser for parsing a URI input as a server command
 */
public class URIParser {

    /**
     * Parses a URI into the information between slashes
     *
     * @param uri a string representation of a URI
     * @return An array containing the strings between slashes in the URI
     */
    public static ArrayList<String> parse(String uri) {
        ArrayList<String> resultSet = new ArrayList<>();
        uri += "/";

        int lastSlash = 0;
        int numSlash = 0;
        for (int i = 0; i < uri.length(); i++) {
            if (uri.charAt(i) == '/') {
                if (numSlash > 0) {
                    resultSet.add(uri.substring(lastSlash + 1, i));
                }
                lastSlash = i;
                numSlash++;
            }
        }
        return resultSet;
    }
}
