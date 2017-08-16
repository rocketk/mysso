package mysso.authentication.principal;

import mysso.authentication.UsernamePasswordCredential;
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
    private AttributeRepository attributeRepository;

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
        SimplePrincipal principal = new SimplePrincipal(credential.getId());
        Map<String, String> attributes = attributeRepository.getAttributeById(credential.getId());
        principal.setAttributes(attributes);
        return principal;
    }

    public void setAttributeRepository(AttributeRepository attributeRepository) {
        this.attributeRepository = attributeRepository;
    }
}
