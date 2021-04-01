package jsonModels;

/**
 * An object containing an array of names for people in the family tree
 */
public class NameData {

    private String[] data;

    /**
     * Creates a new NameData Object
     *
     * @param data an array of names
     */
    public NameData(String[] data) {
        setData(data);
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }
}
