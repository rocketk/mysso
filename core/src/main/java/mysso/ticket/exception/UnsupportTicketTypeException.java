package mysso.ticket.exception;

/**
 * Created by pengyu on 2017/8/14.
 */
public class UnsupportTicketTypeException extends TicketException {
    public UnsupportTicketTypeException() {
    }

    public UnsupportTicketTypeException(String message) {
        super(message);
    }

    public UnsupportTicketTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportTicketTypeException(Throwable cause) {
        super(cause);
    }

    public UnsupportTicketTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
