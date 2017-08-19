package mysso.authentication.principal;

import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Created by pengyu on 2017/8/5.
 */
public class SimplePrincipalFactory implements PrincipalFactory {
    @NotNull
    private AttributeRepository attributeRepository;
    @Override
    public Principal createPrincipal(String id) {
        Assert.notNull(id);
        SimplePrincipal principal = new SimplePrincipal(id);
        Map<String, String> attributes = attributeRepository.getAttributeById(id);
        principal.setAttributes(attributes);
        return principal;
    }

    public void setAttributeRepository(AttributeRepository attributeRepository) {
        this.attributeRepository = attributeRepository;
    }
}
