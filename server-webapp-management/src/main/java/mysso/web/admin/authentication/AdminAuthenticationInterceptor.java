package mysso.web.admin.authentication;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by pengyu.
 */
public class AdminAuthenticationInterceptor extends HandlerInterceptorAdapter {
    private WebUtils webUtils;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean isAuthenticated = webUtils.isAuthenticated(request, response);
        if (isAuthenticated) {
            return true;
        }
        response.sendRedirect(request.getContextPath()+"/login");
        return false;
    }

    public WebUtils getWebUtils() {
        return webUtils;
    }

    public void setWebUtils(WebUtils webUtils) {
        this.webUtils = webUtils;
    }
}
