package mysso.ticket;

import java.io.Serializable;

/**
 * Created by pengyu on 2017/8/17.
 */
public class TicketValidateResult implements Serializable {
    private int code;
    private String message;
    private Token token;

    public TicketValidateResult() {
    }

    public TicketValidateResult(int code, String message, Token token) {
        this.code = code;
        this.message = message;
        this.token = token;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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
