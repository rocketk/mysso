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
}
