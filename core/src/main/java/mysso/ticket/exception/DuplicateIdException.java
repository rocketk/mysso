package mysso.ticket.exception;

/**
 * Created by pengyu.
 */
public class DuplicateIdException extends TicketException {
    public DuplicateIdException() {
    }

    public DuplicateIdException(String message) {
        super(message);
    }

    public DuplicateIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateIdException(Throwable cause) {
        super(cause);
    }

    public DuplicateIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
