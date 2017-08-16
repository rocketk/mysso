package mysso.authentication.handler;

import mysso.authentication.exception.AuthenticationException;
import mysso.authentication.principal.Credential;

/**
 * Created by pengyu on 2017/8/5.
 */
public interface AuthenticationHandler {
    boolean supports(Credential credential);
    HandlerResult authenticate(Credential credential) throws AuthenticationException;
}
