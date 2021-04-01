package results;

/**
 * The response to the clear, fill and load requests
 * The parent class for all other results
 */
public class Result {
    private String message;
    private boolean success;

    /**
     * Creates a new Result
     *
     * @param success a boolean reflecting whether the operation was successful or not
     * @param message a description of the error which occurred
     */
    public Result(boolean success, String message) {
        setSuccess(success);
        setMessage(message);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
