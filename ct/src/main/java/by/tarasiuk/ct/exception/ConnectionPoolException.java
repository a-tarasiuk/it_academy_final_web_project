package by.tarasiuk.ct.exception;

/**
 * Exception, that throws by Pool layer.
 */
public class ConnectionPoolException extends Exception {
    /**
     * Default constructors.
     */
    public ConnectionPoolException() {
        super();
    }

    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(Throwable cause) {
        super(cause);
    }
}