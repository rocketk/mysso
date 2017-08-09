package mysso.authentication;

import mysso.authentication.principal.AttributeRepository;
import org.apache.commons.lang3.Validate;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Created by pengyu on 2017/8/5.
 */
public class SimpleUserAttributeRepository implements AttributeRepository {
    @NotNull
    private Map<String, Map<String, String>> userAttributeMaps;

    public SimpleUserAttributeRepository(Map<String, Map<String, String>> userAttributeMaps) {
        this.userAttributeMaps = userAttributeMaps;
    }

    @Override
    public Map<String, String> getAttributeById(String id) {
        Validate.notNull(userAttributeMaps, "userAttributeMaps cannot be null");
        return userAttributeMaps.get(id);
    }
}
