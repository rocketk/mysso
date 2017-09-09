package mysso.logout;

import java.util.List;

/**
 * Created by pengyu on 2017/8/29.
 */
public class LogoutResult {
    private boolean isAllSuccess;
    private List<LogoutServiceInstance> successLogoutServiceInstance;
    private List<LogoutServiceInstance> failLogoutServiceInstance;

    public boolean isAllSuccess() {
        return isAllSuccess;
    }

    public void setAllSuccess(boolean allSuccess) {
        isAllSuccess = allSuccess;
    }

    public List<LogoutServiceInstance> getSuccessLogoutServiceInstance() {
        return successLogoutServiceInstance;
    }

    public void setSuccessLogoutServiceInstance(List<LogoutServiceInstance> successLogoutServiceInstance) {
        this.successLogoutServiceInstance = successLogoutServiceInstance;
    }

    public List<LogoutServiceInstance> getFailLogoutServiceInstance() {
        return failLogoutServiceInstance;
    }

    public void setFailLogoutServiceInstance(List<LogoutServiceInstance> failLogoutServiceInstance) {
        this.failLogoutServiceInstance = failLogoutServiceInstance;
    }
}
