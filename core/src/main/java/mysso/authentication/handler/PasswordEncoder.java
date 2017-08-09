package mysso.authentication.handler;

/**
 * Created by pengyu on 2017/8/5.
 */
public interface PasswordEncoder {
    String encode(String password);
}
