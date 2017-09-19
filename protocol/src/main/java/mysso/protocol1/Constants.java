package mysso.protocol1;

/**
 * mysso-protocol 1.0 中所规定的常量值
 * Created by pengyu on 17-8-19.
 */
public class Constants {
    /*------------ codes for ticket validation, begin ------------*/
    /** ticket正确并且可以继续使用，客户端不需要更换token */
    public static final int VALID_TICKET = 200;
    /** 仅用于校验token，表示token正确，但是已过期，客户端需要更换token */
    public static final int VALID_BUT_EXPIRED = 201;
    /** service ticket 不正确，客户端应阻止登录，并且引导用户到mysso服务端重新认证 */
    public static final int INVALID_ST = 301;
    /** service ticket 已过期，客户端应阻止登录，并且引导用户到mysso服务端重新认证 */
    public static final int EXPIRED_ST = 302;
    /** token 不正确，客户端应阻止登录，并且引导用户到mysso服务端重新认证 */
    public static final int INVALID_TK = 311;
    /** ticket granting ticket 过期，客户端应阻止登录，并且引导用户到mysso服务端重新认证 */
    public static final int EXPIRED_TGT = 321;
    /** ticket granting ticket 不正确，客户端应阻止登录，并且引导用户到mysso服务端重新认证 */
    public static final int INVALID_TGT = 322;
    /** spid 不正确，客户端应阻止登录，并且给予用户适当提示 */
    public static final int INVALID_SPID = 401;
    /** spid 与所要校验的 ticket 不匹配，客户端应阻止登录，并且给予用户适当提示 */
    public static final int MISMATCH_SPID = 402;
    /** secret passcode 错误，客户端应阻止登录，并且给予用户适当提示 */
    public static final int INVALID_PASSCODE = 410;
    /** 服务端在处理请求时发生错误，客户端应阻止登录，并且给予用户适当提示或稍后再试 */
    public static final int ERROR = 500;
    /*------------ codes for ticket validation, end ------------*/

    /*------------ names for parameter in a validation request, begin ------------*/
    /** 参数 spid 在http请求中的参数名 */
    public static final String PARAM_SPID = "spid";
    /** 参数 secret passcode 在http请求中的参数名 */
    public static final String PARAM_SECRET_PASSCODE = "passcode";
    /** 参数 tk 在http请求中的参数名 */
    public static final String PARAM_TOKEN = "tk";
    /** 参数 st 在http请求中的参数名 */
    public static final String PARAM_SERVICE_TICKET = "st";
    /*------------ names for parameter in a validation request, end ------------*/

    /*------------ uri, begin ------------*/
    /** 用于校验 service ticket 的相对路径 */
    public static final String VALIDATE_ST_URI = "/validate/st";
    /** 用于校验 token 的相对路径 */
    public static final String VALIDATE_TK_URI = "/validate/tk";
    /*------------ uri, end ------------*/

    /*------------ SLO(single logout), begin ------------*/
    public static final String SLO_PARAM_TOKEN = "tk";
    public static final int SLO_CODE_SUCCESS = 200;
    public static final int SLO_CODE_TOKEN_NONEXISTS = 201;
    public static final int SLO_CODE_TOKEN_EMPTY = 301;
    public static final int SLO_CODE_ERROR = 500;
    /*------------ SLO(single logout), end ------------*/
}
