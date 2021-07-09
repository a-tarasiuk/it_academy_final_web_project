package by.tarasiuk.ct.exception;

public class CryptException extends Exception {
    public CryptException() {
        super();
    }

    public CryptException(String message, Throwable cause) {
        super(message, cause);
    }

    public CryptException(String message) {
        super(message);
    }

    public CryptException(Throwable cause) {
        super(cause);
    }
}