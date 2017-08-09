package mysso.authentication.handler;

import mysso.authentication.principal.Credential;
import mysso.authentication.UsernamePasswordCredential;

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
        return credential instanceof UsernamePasswordCredential;
    }

    @Override
    public boolean authenticate(Credential credential) {
        // TODO
        return true;
    }

    public void setUsers(Map<String, String> users) {
        this.users = users;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
