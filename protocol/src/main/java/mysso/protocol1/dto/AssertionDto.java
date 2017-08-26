package mysso.protocol1.dto;


import java.io.Serializable;

/**
 * 用于封装server端向client端返回ticket校验结果的实体类
 * ticket既可以指ServiceTicket也可以指Token
 * Created by pengyu on 17-8-19.
 */
public class AssertionDto implements Serializable {
    /**
     * 表示校验结果的数值，取值范围参见 mysso.common.protocol1.Constants
     */
    private int code;
    /**
     * 提示信息
     */
    private String message;
    /**
     * 需要派发新的token时（例如st校验通过后或者tk超时需要更换），此字段不为null。否则为null。
     * 此字段实际上对应的是Token对象的id字段
     */
    private String token; // tokenId
    /**
     * token过期时间
     */
    private long expiredTime;
    /**
     * 用户信息
     */
    private PrincipalDto principal;

    public AssertionDto() {
    }

    public AssertionDto(int code, String message) {
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

    public PrincipalDto getPrincipal() {
        return principal;
    }

    public void setPrincipal(PrincipalDto principal) {
        this.principal = principal;
    }
}
