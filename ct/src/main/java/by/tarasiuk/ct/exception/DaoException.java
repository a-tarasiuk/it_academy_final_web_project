package by.tarasiuk.ct.exception;

/**
 * Exception, that throws by Dao layer.
 */
public class DaoException extends Exception {
    /**
     * Default constructors.
     */
    public DaoException() {
        super();
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}