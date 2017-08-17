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

}





