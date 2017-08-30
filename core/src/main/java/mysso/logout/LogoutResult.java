package mysso.logout;

import java.util.List;

/**
 * Created by pengyu on 2017/8/29.
 */
public class LogoutResult {
    private boolean isAllSuccess;
    private List<LogoutServiceInstance> successLogoutServiceInstance;
    private List<LogoutServiceInstance> failLogoutServiceInstance;
}
