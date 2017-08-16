package mysso.authentication;

import mysso.authentication.principal.Credential;
import mysso.ticket.ServiceTicket;
import mysso.ticket.Token;

/**
 * Created by pengyu on 2017/8/5.
 */
public interface AuthenticationManager {
    /**
     * 对credential进行认证，认证结果放到Authentication中
     * @param credential
     * @return
     */
    Authentication authenticate(Credential credential);

    /**
     * 校验serviceTicket，校验结果放到Assertion中，如果校验通过，则会生成一个token放入Assertion中
     * @param serviceTicket
     * @return
     */
    Assertion validateServiceTicket(ServiceTicket serviceTicket);

    /**
     * 校验token，校验结果放到Assertion中，如果需要更换token，则会将新token一并放入Assertion中
     * @param token
     * @return Assertion
     */
    Assertion validateToken(Token token);
}
