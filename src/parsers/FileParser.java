package parsers;

import java.io.*;
import java.util.Scanner;

/**
 * Parser for parsing the files in the json folder
 * Accessed by classes in the generators package
 */
public class FileParser {

    /**
     * Parses a json file into a stringbuilder
     *
     * @param pathname the filepath where the json file is stored
     * @return a stringbuilder of the contents of the json file
     * @throws IOException if an input/output error occurs
     */
    public static StringBuilder parse(String pathname) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            File file = new File(pathname);
            Scanner scan = new Scanner(file);
            while (scan.hasNext()) {
                stringBuilder.append(scan.next());
            }
        }
        catch(IOException exception) {
            throw new IOException();
        }
        return stringBuilder;
    }
}