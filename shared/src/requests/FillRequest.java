package requests;

/**
 * The request for the fill command
 */
public class FillRequest {

    private String username;
    private int generations;

    /**
     * Creates a new FillRequest when the number of generations is provided
     *
     * @param username the username of the user whose family tree will be filled
     * @param generations the number of generations the filled family tree will include
     */
    public FillRequest(String username, int generations) {
        setUsername(username);
        setGenerations(generations);
    }

    /**
     * Creates a new FillRequest when no number of generations is provided
     * The number of generations defaults to 4
     *
     * @param username the username of the user whose family tree will be filled
     */
    public FillRequest(String username) {
        setUsername(username);
        setGenerations(4);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGenerations() {
        return generations;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }
}
