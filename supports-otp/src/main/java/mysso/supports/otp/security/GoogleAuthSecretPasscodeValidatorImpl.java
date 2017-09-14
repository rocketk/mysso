package mysso.supports.otp.security;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import mysso.security.SecretPasscodeValidator;

/**
 * Created by pengyu on 2017/9/14.
 */
public class GoogleAuthSecretPasscodeValidatorImpl implements SecretPasscodeValidator {
    @Override
    public boolean validateSecretPasscode(String secret, String passcode) {

        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        gAuth.authorize()
        return false;
    }
}
