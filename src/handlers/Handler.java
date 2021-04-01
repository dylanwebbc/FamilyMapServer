package handlers;

import java.io.*;

/**
 * The parent class of all handlers
 */
public class Handler {

    /**
     * Reads an InputStream and converts it to a StringBuilder
     *
     * @param is an InputStream
     * @return a StringBuilder representation of the InputStream
     * @throws IOException if an input/output error occurs
     */
    protected String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    /**
     * Writes a string to an OutputStream
     *
     * @param str the string to be written
     * @param os the OutputStream to be written to
     * @throws IOException if an input/output error occurs
     */
    protected void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

}
