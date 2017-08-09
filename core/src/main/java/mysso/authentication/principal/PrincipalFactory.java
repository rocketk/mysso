package mysso.authentication.principal;

import java.util.Map;

/**
 * Created by pengyu on 2017/8/5.
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
     * Create principal along with its attributes.
     *
     * @param id the id
     * @param attributes the attributes
     * @return the principal
     */
    Principal createPrincipal(String id, Map<String, String> attributes);
}
