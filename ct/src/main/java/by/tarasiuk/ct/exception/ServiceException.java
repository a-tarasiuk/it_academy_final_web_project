package by.tarasiuk.ct.exception;

/**
 * Exception, that throws by Service layer.
 */
public class ServiceException extends Exception {
    /**
     * Default constructors.
     */
    public ServiceException() {
        super();
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}