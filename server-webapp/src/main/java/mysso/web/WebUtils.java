package mysso.web;

import mysso.authentication.Authentication;
import mysso.session.Session;
import mysso.session.registry.SessionRegistry;
import mysso.ticket.TicketGrantingTicket;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by pengyu.
 */
public class WebUtils {
    private String tgcNameInCookie;
    private String sessionIdNameInCookie;
    private String authNameInSession;
    private SessionRegistry sessionRegistry;

    public boolean isAuthenticated(HttpServletRequest request, HttpServletResponse response) {
        // check tgc from cookies
        Cookie tgc = extractCookieByName(request, tgcNameInCookie);
        if (tgc == null || tgc.getValue() == null) {
            return false;
        }
        String tgtId = tgc.getValue();
        // check authentication from session
        Object authenticationObj = getAuthenticationFromSession(request);
        if (authenticationObj == null) {
//            deleteCookieByName(response, tgc.getName());
            return false;
        }
        Authentication authentication = (Authentication) authenticationObj;
        // tgtId from cookies have to equal to the one from session
        TicketGrantingTicket tgt = authentication.getTicketGrantingTicket();
        if (StringUtils.equals(tgtId, tgt.getId()) && !tgt.isExpired()) {
            return true;
        }
//        deleteCookieByName(response, tgc.getName());
        return false;
    }

    public Authentication getAuthenticationFromSession(HttpServletRequest request){
        Object authenticationObj = getSessionFromRequest(request).get(authNameInSession);
        if (authenticationObj == null) {
            return null;
        }
        return (Authentication) authenticationObj;
    }

    public void putAuthenticationToSession(HttpServletRequest request, Authentication authentication) {
        Session session = getSessionFromRequest(request);
        session.put(authNameInSession, authentication);
    }

    public Cookie extractCookieByName(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
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

    /**
     * 使session中的tgt过期
     * @param request
     */
    public void expireTGT(HttpServletRequest request) {
        Session session = getSessionFromRequest(request);
        if (session != null) {
            Authentication authentication = (Authentication) session.get(authNameInSession);
            authentication.getTicketGrantingTicket().markExpired();
        }
    }

    /**
     * 销毁当前用户的登录信息，包括session，cookie
     * 并使TGT过期
     * @param request
     * @param response
     * @return TicketGrantingTicket 返回已过期的TicketGrantingTicket，返回null时表示当前用户此前已登出
     */
    public TicketGrantingTicket destroySession(HttpServletRequest request, HttpServletResponse response) {
        // delete tgc from cookies
        deleteCookieByName(response, tgcNameInCookie);
        deleteCookieByName(response, sessionIdNameInCookie);
        Session session = getSessionFromRequest(request);
        if (session != null) {
            // invalidate TGT
            Authentication authentication = (Authentication) session.get(authNameInSession);
            TicketGrantingTicket ticketGrantingTicket = authentication.getTicketGrantingTicket();
            ticketGrantingTicket.markExpired();
            // invalidate session
            sessionRegistry.remove(session);
            return ticketGrantingTicket;
        }
        return null;
    }

    public Session getSessionFromRequest(HttpServletRequest request) {
        Cookie cookie = extractCookieByName(request, sessionIdNameInCookie);
        if (cookie != null) {
            String sessionId = cookie.getValue();
            return sessionRegistry.getSession(sessionId);
        }
        return null;
    }

    public void setTgcNameInCookie(String tgcNameInCookie) {
        this.tgcNameInCookie = tgcNameInCookie;
    }

    public void setAuthNameInSession(String authNameInSession) {
        this.authNameInSession = authNameInSession;
    }

    public void setSessionIdNameInCookie(String sessionIdNameInCookie) {
        this.sessionIdNameInCookie = sessionIdNameInCookie;
    }

    public void setSessionRegistry(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }
}
