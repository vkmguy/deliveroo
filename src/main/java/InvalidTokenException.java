public class InvalidTokenException extends Exception {
    private static final long serialVersionUID = 1L;
    InvalidTokenException(String message) {
        super(message);
    }

    InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}