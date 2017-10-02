package mysso.web;

import mysso.authentication.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by pengyu.
 */
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    private Logger log = LoggerFactory.getLogger(getClass());

    private WebUtils webUtils;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean isAuthenticated = webUtils.isAuthenticated(request, response);
        if (isAuthenticated) {
            Authentication authentication = webUtils.getAuthenticationFromSession(request);
            request.setAttribute("authentication", authentication);
            return true;
        }
        response.sendRedirect(request.getContextPath()+"/login");
        return false;
    }

    public void setWebUtils(WebUtils webUtils) {
        this.webUtils = webUtils;
    }
}
