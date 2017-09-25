package mysso.authentication.handler;

/**
 * Created by pengyu.
 */
public interface PasswordEncoder {
    String encode(String password);
}
