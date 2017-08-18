package mysso.authentication.credential;

import java.util.Map;

/**
 * Created by pengyu on 17-8-17.
 */
public interface CredentialFactory {
    Credential createCredential(Map<String, String> params);
}
