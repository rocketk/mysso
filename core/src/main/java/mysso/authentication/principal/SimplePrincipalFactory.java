package mysso.authentication.principal;

import java.util.Map;

/**
 * Created by pengyu on 2017/8/5.
 */
public class SimplePrincipalFactory implements PrincipalFactory {
    @Override
    public Principal createPrincipal(String id) {
        return new SimplePrincipal(id);
    }

    @Override
    public Principal createPrincipal(String id, Map<String, String> attributes) {
        return new SimplePrincipal(id, attributes);
    }
}
