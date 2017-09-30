package mysso.web.admin.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by pengyu.
 */
public class WebUtils {
    private String authNameInSession;

    public WebUtils(String authNameInSession) {
        this.authNameInSession = authNameInSession;
    }

    public boolean isAuthenticated(HttpServletRequest request, HttpServletResponse response) {
        // check authentication from session
        Object adminAuthObj = request.getSession().getAttribute(authNameInSession);
        return adminAuthObj != null;
    }

    public AdminAuthentication getAuthenticationFromSession(HttpServletRequest request){
        Object adminAuthObj = request.getSession().getAttribute(authNameInSession);
        if (adminAuthObj == null) {
            return null;
        }
        return (AdminAuthentication) adminAuthObj;
    }

}
