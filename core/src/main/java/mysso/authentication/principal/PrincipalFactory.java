package mysso.authentication.principal;

import java.util.Map;

/**
 * Created by pengyu.
 */
public interface PrincipalFactory {
    /**
     * Create principal.
     *
     * @param id the id
     * @return the principal
     */
    Principal createPrincipal(String id);
    /**
     * Create principal.
     *
     * @param id the id
     * @param attributes attributes
     * @return the principal
     */
    Principal createPrincipal(String id, Map<String, Object> attributes);
}
