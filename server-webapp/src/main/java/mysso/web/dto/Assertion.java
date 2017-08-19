package mysso.web.dto;

import mysso.authentication.principal.Principal;
import mysso.ticket.Token;

import java.util.Map;

/**
 * ticket校验结果
 * ticket既可以指ServiceTicket也可以指Token
 * Created by pengyu on 17-8-19.
 */
public class Assertion {
    /**
     *
     */
    private int code;
    private String message;
    /**
     * 需要派发新的token时（例如st校验通过后或者tk超时需要更换），此字段不为空。否则为null
     */
    private String token; // tokenId
    private long expiredTime;
    private Principal principal;

    public Assertion() {
    }

    public Assertion(int code, String message) {
        this.code = code;
        this.message = message;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(long expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }
}
