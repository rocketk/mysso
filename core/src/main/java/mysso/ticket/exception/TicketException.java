package mysso.ticket.exception;

/**
 * Created by pengyu.
 */
public class TicketException extends RuntimeException {
//    public static final String UNSUPPORTED_TICKET_TYPE = "the ticket type is unsupported: {}";
//    public static final String DUPLICATED_ID = "the id of the ticket to be added has already exists: {}";

    public TicketException() {
    }

    public TicketException(String message) {
        super(message);
    }

    public TicketException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketException(Throwable cause) {
        super(cause);
    }

    public TicketException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
