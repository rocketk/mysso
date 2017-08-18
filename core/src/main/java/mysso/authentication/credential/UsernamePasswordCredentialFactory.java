package mysso.authentication.credential;

import org.springframework.util.Assert;

import java.util.Map;

/**
 * Created by pengyu on 17-8-17.
 */
public class UsernamePasswordCredentialFactory implements CredentialFactory {
    private String keyForUsername = "username";
    private String keyForPassword = "password";
    @Override
    public Credential createCredential(Map<String, String> params) {
        Assert.notNull(params);
        Object username = params.get(keyForUsername);
        Object password = params.get(keyForPassword);
        Credential credential = new UsernamePasswordCredential(
                username==null?null:(String)username,
                password==null?null:(String)password
        );
        return credential;
    }

    public void setKeyForUsername(String keyForUsername) {
        this.keyForUsername = keyForUsername;
    }

    public void setKeyForPassword(String keyForPassword) {
        this.keyForPassword = keyForPassword;
    }
}
