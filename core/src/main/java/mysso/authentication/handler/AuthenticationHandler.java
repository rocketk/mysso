package mysso.authentication.handler;

import mysso.authentication.credential.Credential;
import mysso.authentication.exception.AuthenticationException;

/**
 * Created by pengyu.
 */
public interface AuthenticationHandler {
    boolean supports(Credential credential);
    HandlerResult authenticate(Credential credential) throws AuthenticationException;
}
