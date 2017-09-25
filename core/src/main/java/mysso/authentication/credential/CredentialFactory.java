package mysso.authentication.credential;

import java.util.Map;

/**
 * Created by pengyu.
 */
public interface CredentialFactory {
    Credential createCredential(Map<String, String> params);
}
