package mysso.authentication.principal;

import org.apache.commons.lang3.Validate;

import java.util.Map;

/**
 * Created by pengyu on 2017/8/5.
 */
public class SimplePrincipalFactory implements PrincipalFactory {
    @Override
    public Principal createPrincipal(String id) {
        Validate.notNull(id);
        return createPrincipal(id, null);
    }

    @Override
    public Principal createPrincipal(String id, Map<String, Object> attributes) {
        Validate.notNull(id);
        return new SimplePrincipal(id, attributes);
    }

}
