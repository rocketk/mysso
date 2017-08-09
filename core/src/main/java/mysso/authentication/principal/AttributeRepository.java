package mysso.authentication.principal;

import java.util.Map;

/**
 * Created by pengyu on 2017/8/5.
 */
public interface AttributeRepository {
    Map<String, String> getAttributeById(String id);
}
