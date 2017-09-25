package mysso.authentication.principal;

import mysso.authentication.credential.Credential;
import mysso.authentication.exception.AuthenticationException;

/**
 * Created by pengyu.
 */
public interface PrincipalResolver {
//    boolean supports(Credential credential);
    Principal resolve(Credential credential) throws AuthenticationException;
    Principal resolve(String credentialId) throws AuthenticationException;
}
