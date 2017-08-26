package mysso.util;

/**
 * Created by pengyu on 17-8-19.
 */
public class Constants {
    /*------------ code for ticket validation begin ------------*/
    public static final int VALID_TICKET = 200;
    /** only for token */
    public static final int VALID_BUT_EXPIRED = 201;
    public static final int INVALID_ST = 301;
    public static final int EXPIRED_ST = 302;
    public static final int INVALID_TK = 311;
    public static final int INVALID_SPID = 401;
    public static final int MISMATCH_SPID = 402;
    public static final int INVALID_SPKEY = 410;
    public static final int ERROR = 500;
    /*------------ code for ticket validation end ------------*/
}
