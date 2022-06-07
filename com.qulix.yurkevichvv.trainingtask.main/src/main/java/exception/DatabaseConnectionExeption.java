package exception;

public class DatabaseConnectionExeption extends Exception {

    public DatabaseConnectionExeption(String message) {
        super(message);
    }

    public DatabaseConnectionExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseConnectionExeption(Throwable cause) {
        super(cause);
    }
}
