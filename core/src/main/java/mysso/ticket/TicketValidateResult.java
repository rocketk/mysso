package mysso.ticket;

import java.io.Serializable;

/**
 * Created by pengyu on 2017/8/17.
 */
public class TicketValidateResult implements Serializable {
    private TicketStatus status;
    private String message;
    private Token token;

    public TicketValidateResult() {
    }

    public TicketValidateResult(TicketStatus status, String message, Token token) {
        this.status = status;
        this.message = message;
        this.token = token;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
