package exception;

public class EntityFailedException extends Exception {

    public EntityFailedException(String message) {
        super(message);
    }

    public EntityFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityFailedException(Throwable cause) {
        super(cause);
    }
}

