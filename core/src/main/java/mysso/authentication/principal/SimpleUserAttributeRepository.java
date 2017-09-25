package mysso.authentication.principal;

import org.apache.commons.lang3.Validate;

import java.util.Map;

/**
 * Created by pengyu.
 */
public class SimpleUserAttributeRepository implements AttributeRepository {

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
