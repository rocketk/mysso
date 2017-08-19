package mysso.authentication.principal;

import mysso.authentication.credential.UsernamePasswordCredential;
import mysso.authentication.credential.Credential;
import mysso.authentication.exception.AuthenticationException;
import mysso.authentication.exception.CredentialNotSupportedException;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.Map;

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
        if (!supports(credential)) {
            throw new CredentialNotSupportedException(String.format("the credential %s is not supported by this principal resolver %s",
                    credential.getClass().getSimpleName(), this.getClass().getSimpleName())
            );
        }
        return principalFactory.createPrincipal(credential.getId());
    }

    public void setPrincipalFactory(PrincipalFactory principalFactory) {
        this.principalFactory = principalFactory;
    }
}
