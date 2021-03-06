package mysso.security;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.apache.commons.lang3.Validate;

/**
 * Created by pengyu.
 */
public class GoogleAuthSecretPasscodeValidator implements SecretPasscodeValidator {
    protected final GoogleAuthenticator googleAuthenticator;

    public GoogleAuthSecretPasscodeValidator(GoogleAuthenticator googleAuthenticator) {
        this.googleAuthenticator = googleAuthenticator;
    }

    @Override
    public boolean validateSecretPasscode(String secret, String passcode) {
        Validate.notNull(secret, "secret is null");
        Validate.notNull(passcode, "passcode is null");
        return googleAuthenticator.authorize(secret, Integer.parseInt(passcode));
    }
}
