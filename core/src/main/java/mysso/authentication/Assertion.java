package mysso.authentication;

import mysso.ticket.Token;

import java.io.Serializable;

/**
 * Created by pengyu on 2017/8/14.
 */
public class Assertion implements Serializable {
    private int code;
    private Token token;
    private long nextTimeToValide;

    public Assertion() {
    }

    public Assertion(int code, Token token, long nextTimeToValide) {
        this.code = code;
        this.token = token;
        this.nextTimeToValide = nextTimeToValide;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public long getNextTimeToValide() {
        return nextTimeToValide;
    }

    public void setNextTimeToValide(long nextTimeToValide) {
        this.nextTimeToValide = nextTimeToValide;
    }
}
