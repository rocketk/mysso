package mysso.authentication;

import mysso.authentication.principal.Credential;

/**
 * Created by pengyu on 2017/8/5.
 */
public interface AuthenticationManager {
    Authentication authenticate(Credential credential);
}
