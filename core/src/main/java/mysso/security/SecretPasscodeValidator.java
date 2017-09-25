package mysso.security;

/**
 * Created by pengyu.
 */
public interface SecretPasscodeValidator {
    /**
     * 校验给定的passcode是否匹配给定的secret
     * @param secret
     * @param passcode
     * @return
     */
    boolean validateSecretPasscode(String secret, String passcode);
}
