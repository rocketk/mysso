package mysso.authentication.principal;

import mysso.authentication.UsernamePasswordCredential;
import mysso.authentication.exception.AuthenticationException;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;

/**
 * Created by pengyu on 2017/8/5.
 */
public class SimplePrincipalResolver implements PrincipalResolver {

    @NotNull
    private PrincipalFactory principalFactory;
    @Override
    public boolean supports(Credential credential) {
        Assert.notNull(credential);
        return credential instanceof UsernamePasswordCredential;
    }

    @Override
    public Principal resolve(Credential credential) throws AuthenticationException {
        Assert.notNull(credential);

        return null;
    }
}
