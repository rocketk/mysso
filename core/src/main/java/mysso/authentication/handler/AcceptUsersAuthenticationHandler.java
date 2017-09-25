package mysso.authentication.handler;

import mysso.authentication.credential.Credential;
import mysso.authentication.credential.UsernamePasswordCredential;
import mysso.authentication.exception.CredentialNotSupportedException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by pengyu.
 */
public class AcceptUsersAuthenticationHandler implements AuthenticationHandler {

    private Logger log = LoggerFactory.getLogger(getClass());


    private PasswordEncoder passwordEncoder;

    private Map<String, String> users;

    @Override
    public boolean supports(Credential credential) {
        Validate.notNull(credential);
        return credential instanceof UsernamePasswordCredential;
    }

    @Override
    public HandlerResult authenticate(Credential credential) {
        Validate.notNull(credential);
        if (!supports(credential)) {
            log.error("the credential {} is not supported by the handler {}",
                    credential.getClass().getSimpleName(), this.getClass().getSimpleName());
            throw new CredentialNotSupportedException(String.format("the credential %s is not supported by the handler %s",
                    credential.getClass().getSimpleName(), this.getClass().getSimpleName())
            );
        }
        if (!users.containsKey(credential.getId())) {
            log.info("the user id {} does not exist", credential.getId());
            return new HandlerResult(false, "user does not exist");
        }

        UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) credential;
        String encodedPassword = passwordEncoder.encode(usernamePasswordCredential.getPassword());
        String passwordFromRepository = users.get(credential.getId());
        if (StringUtils.equals(encodedPassword, passwordFromRepository)) {
            log.info("user id {} is authenticated successfully", credential.getId());
            return new HandlerResult(true, "authenticated successfully");
        }
        log.info("invalid password, id %s", credential.getId());
        return new HandlerResult(false, "invalid password");
    }

    public void setUsers(Map<String, String> users) {
        this.users = users;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
