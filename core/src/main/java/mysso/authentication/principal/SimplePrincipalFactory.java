package mysso.authentication.principal;

import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Created by pengyu on 2017/8/5.
 */
public class SimplePrincipalFactory implements PrincipalFactory {
    @Override
    public Principal createPrincipal(String id) {
        Assert.notNull(id);
        return createPrincipal(id, null);
    }

    @Override
    public Principal createPrincipal(String id, Map<String, Object> attributes) {
        Assert.notNull(id);
        return new SimplePrincipal(id, attributes);
    }

}
