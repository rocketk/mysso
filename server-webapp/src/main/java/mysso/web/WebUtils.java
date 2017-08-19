package mysso.web;

import mysso.authentication.Authentication;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by pengyu on 17-8-18.
 */
public class WebUtils {
    private String tgcNameInCookie;
    private String authNameInSession;
    public boolean isAuthenticated(HttpServletRequest request, HttpServletResponse response) {
        // check tgc from cookies
        Cookie tgc = extractCookieByName(request, tgcNameInCookie);
        if (tgc == null || tgc.getValue() == null) {
            return false;
        }
        String tgtId = tgc.getValue();
        // check authentication from session
        Object authenticationObj = request.getSession().getAttribute(authNameInSession);
        if (authenticationObj == null) {
            deleteCookieByName(response, tgc.getName());
            return false;
        }
        Authentication authentication = (Authentication) authenticationObj;
        // tgtId from cookies have to equal to the one from session
        if (StringUtils.equals(tgtId, authentication.getTicketGrantingTicket().getId())) {
            return true;
        }
        deleteCookieByName(response, tgc.getName());
        return false;
    }

    public Cookie extractCookieByName(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if(name.equals(cookie.getName())){
                    return cookie;
                }
            }
        }
        return null;
    }

    public void deleteCookieByName(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public void setTgcNameInCookie(String tgcNameInCookie) {
        this.tgcNameInCookie = tgcNameInCookie;
    }

    public void setAuthNameInSession(String authNameInSession) {
        this.authNameInSession = authNameInSession;
    }
}
