package mysso.authentication.principal;

import mysso.authentication.AuthenticationException;

import javax.validation.constraints.NotNull;

/**
 * Created by pengyu on 2017/8/5.
 */
public class SimplePrincipalResolver implements PrincipalResolver {

    @NotNull
    private PrincipalFactory principalFactory;
    @Override
    public boolean supports(Credential credential) {
        // always true
        return true;
    }

    @Override
    public Principal resolve(Credential credential) throws AuthenticationException {
        return null;
    }
}
