package mysso.ticket.exception;

/**
 * Created by pengyu.
 */
public class TicketIdNotExistsException extends TicketException {
    public TicketIdNotExistsException() {
    }

    public TicketIdNotExistsException(String message) {
        super(message);
    }

    public TicketIdNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketIdNotExistsException(Throwable cause) {
        super(cause);
    }

    public TicketIdNotExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
