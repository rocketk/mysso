package mysso.supports.otp.security;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by pengyu on 2017/9/18.
 */
public class GoogleAutenticatorSecretPasscodeValidatorTest {
    protected GoogleAuthSecretPasscodeValidator secretPasscodeValidator;
    protected GoogleAuthenticator googleAuthenticator;

    @Before
    public void setUp() {
        final ApplicationContext context = new ClassPathXmlApplicationContext("/secretPasscodeValidator-test.xml");
        secretPasscodeValidator = context.getBean("googleAuthSecretPasscodeValidator", GoogleAuthSecretPasscodeValidator.class);
        googleAuthenticator = new GoogleAuthenticator();
    }

    @Test
    public void verifyValidateSecretPasscode() {
        String secret = "0123456789ABCDEF";
        int totpPasscode = googleAuthenticator.getTotpPassword(secret);
        // 验证成功
        Assert.assertTrue(secretPasscodeValidator.validateSecretPasscode(secret, totpPasscode + ""));
        // 随意填写的校验码，在绝大多数下应该是验证失败
        Assert.assertFalse(secretPasscodeValidator.validateSecretPasscode(secret, 123456 + ""));
    }
}
