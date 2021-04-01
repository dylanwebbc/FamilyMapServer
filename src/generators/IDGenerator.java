package generators;

import java.util.UUID;

/**
 * Generates random IDs to be used as personIDs, authtokens, and eventIDs
 */
public class IDGenerator {

    /**
     * Generates a unique 128-bit value
     *
     * @return the random value as a string
     */
    public static String generateID() {
        return UUID.randomUUID().toString();
    }

}
