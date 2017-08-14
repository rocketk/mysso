package mysso.authentication;

import mysso.authentication.principal.Credential;
import mysso.ticket.ServiceTicket;
import mysso.ticket.Token;

/**
 * Created by pengyu on 2017/8/5.
 */
public interface AuthenticationManager {
    /**
     * authenticate the credential
     * @param credential
     * @return
     */
    Authentication authenticate(Credential credential);

    Assertion validateServiceTicket(ServiceTicket serviceTicket);

    Assertion validateToken(Token token);
}
