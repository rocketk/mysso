package mysso.authentication.principal;

import java.util.Map;

/**
 * Created by pengyu.
 */
public interface AttributeRepository {
    Map<String, Object> getAttributeById(String id);
}
