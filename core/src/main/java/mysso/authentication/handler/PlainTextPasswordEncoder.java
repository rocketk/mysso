package mysso.authentication.handler;

/**
 * Created by pengyu.
 */
public final class PlainTextPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(String password) {
        return password;
    }
}
