package mysso.authentication.principal;

import mysso.authentication.credential.Credential;
import mysso.authentication.exception.AuthenticationException;
import org.apache.commons.lang3.Validate;

import java.util.Map;

/**
 * Created by pengyu on 2017/8/5.
 */
public class AttributedPrincipalResolver implements PrincipalResolver {


    private AttributeRepository attributeRepository;

//    @Override
//    public boolean supports(Credential credential) {
//        Validate.notNull(credential);
//        return credential instanceof UsernamePasswordCredential;
//    }

    @Override
    public Principal resolve(Credential credential) throws AuthenticationException {
        Validate.notNull(credential);
        return resolve(credential.getId());
//        if (!supports(credential)) {
//            throw new CredentialNotSupportedException(String.format("the credential %s is not supported by this principal resolver %s",
//                    credential.getClass().getSimpleName(), this.getClass().getSimpleName())
//            );
//        }
//        SimplePrincipal principal = new SimplePrincipal(credential.getId());
//        Map<String, String> attributes = attributeRepository.getAttributeById(credential.getId());
//        principal.setAttributes(attributes);
//        return principal;
    }

    @Override
    public Principal resolve(String credentialId) throws AuthenticationException {
        SimplePrincipal principal = new SimplePrincipal(credentialId);
        Map<String, Object> attributes = attributeRepository.getAttributeById(credentialId);
        principal.setAttributes(attributes);
        return principal;
    }

    public void setAttributeRepository(AttributeRepository attributeRepository) {
        this.attributeRepository = attributeRepository;
    }
}
