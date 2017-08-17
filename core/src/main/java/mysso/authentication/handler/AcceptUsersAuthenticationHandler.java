package mysso.authentication.handler;

import mysso.authentication.credential.UsernamePasswordCredential;
import mysso.authentication.credential.Credential;
import mysso.authentication.exception.CredentialNotSupportedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Created by pengyu on 2017/8/5.
 */
public class AcceptUsersAuthenticationHandler implements AuthenticationHandler {

    @NotNull
    private PasswordEncoder passwordEncoder;
    @NotNull
    private Map<String, String> users;

    @Override
    public boolean supports(Credential credential) {
        Assert.notNull(credential);
        return credential instanceof UsernamePasswordCredential;
    }

    @Override
    public HandlerResult authenticate(Credential credential) {
        Assert.notNull(credential);
        if (!supports(credential)) {
            throw new CredentialNotSupportedException(String.format("the credential %s is not supported by the handler %s",
                    credential.getClass().getSimpleName(), this.getClass().getSimpleName())
            );
        }
        if (!users.containsKey(credential.getId())) {
            return new HandlerResult(false,
                    String.format("the user id %s does not exist", credential.getId())
            );
        }

        UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) credential;
        String encodedPassword = passwordEncoder.encode(usernamePasswordCredential.getPassword());
        String passwordFromRepository = users.get(credential.getId());
        if (StringUtils.equals(encodedPassword, passwordFromRepository)) {
            return new HandlerResult(true, String.format("user id %s is successfully authenticated", credential.getId()));
        }
        return new HandlerResult(false, String.format("invalid password, id %s", credential.getId()));
    }

    public void setUsers(Map<String, String> users) {
        this.users = users;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
