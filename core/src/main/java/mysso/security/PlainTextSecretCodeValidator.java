package mysso.security;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * SecretPasscodeValidator的简单实现，不建议在生产环境中使用，建议使用GoogleAuthSecretPasscodeValidator替代此类
 * Created by pengyu on 2017/9/14.
 */
public class PlainTextSecretCodeValidator implements SecretPasscodeValidator {
    /**
     * 这里假设应用发送过来的passcode是直接采用secret而没有进行任何转换
     * 因为这样会在通信过程中传递应用的secret，因此不建议在生产环境中使用
     * @param secret
     * @param passcode
     * @return
     */
    @Override
    public boolean validateSecretPasscode(String secret, String passcode) {
        Validate.notNull(secret, "secret is null");
        Validate.notNull(passcode, "passcode is null");
        return StringUtils.equals(secret, passcode);
    }
}
