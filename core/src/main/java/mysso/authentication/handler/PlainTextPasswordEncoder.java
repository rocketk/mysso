package mysso.authentication.handler;

/**
 * Created by pengyu on 2017/8/5.
 */
public final class PlainTextPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(String password) {
        return password;
    }
}
