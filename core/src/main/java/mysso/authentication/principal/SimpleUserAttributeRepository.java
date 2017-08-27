package mysso.authentication.principal;

import mysso.authentication.principal.AttributeRepository;
import org.apache.commons.lang3.Validate;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Created by pengyu on 2017/8/5.
 */
public class SimpleUserAttributeRepository implements AttributeRepository {
    @NotNull
    private Map<String, Map<String, Object>> userAttributeMaps;

    public SimpleUserAttributeRepository(Map<String, Map<String, Object>> userAttributeMaps) {
        this.userAttributeMaps = userAttributeMaps;
    }

    @Override
    public Map<String, Object> getAttributeById(String id) {
        Validate.notNull(userAttributeMaps, "userAttributeMaps is null");
        return userAttributeMaps.get(id);
    }
}
