package mysso.authentication.principal;

import mysso.authentication.AuthenticationException;

/**
 * Created by pengyu on 2017/8/5.
 */
public interface PrincipalResolver {
    boolean supports(Credential credential);
    Principal resolve(Credential credential) throws AuthenticationException;
}
