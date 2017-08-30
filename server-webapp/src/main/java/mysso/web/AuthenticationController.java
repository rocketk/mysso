package mysso.web;

import mysso.authentication.Authentication;
import mysso.authentication.AuthenticationManager;
import mysso.authentication.credential.Credential;
import mysso.authentication.credential.CredentialFactory;
import mysso.protocol1.Constants;
import mysso.serviceprovider.ServiceProvider;
import mysso.serviceprovider.registry.ServiceProviderRegistry;
import mysso.ticket.ServiceTicket;
import mysso.ticket.TicketManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Created by pengyu on 2017/8/5.
 */
@Controller
public class AuthenticationController {

    @NotNull
    private CredentialFactory credentialFactory;

    @NotNull
    private AuthenticationManager authenticationManager;

    @NotNull
    private TicketManager ticketManager;

    @NotNull
    private ServiceProviderRegistry serviceProviderRegistry;

    @NotNull
    private WebUtils webUtils;

    private String authNameInSession;

    private String tgcNameInCookie;

    private String spidNameInParams;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request,
                        HttpServletResponse response,
                        @RequestParam Map<String, String> params) {
        String spid = params.get(spidNameInParams);
        ServiceProvider sp = null;
        // 如果参数中有了spid, 并且这个spid不正确, 则返回warn页面以提示用户
        if (spid != null) {
            sp = serviceProviderRegistry.get(spid);
            if (sp == null) {
                request.setAttribute("warning", "尚未接入的应用：" + spid);
                return "warning";
            }
        }
        // 判断是否已登录
        if (webUtils.isAuthenticated(request, response)) {
            // 如果已登录, 则检查参数spid所表示的 service provider 是否正确
            // 如果没问题, 则重定向到这个 service provider 的主页, 否则重定向到mysso的home页
            if (sp != null) {
                Authentication authentication = (Authentication) request.getSession().getAttribute(authNameInSession);
                ServiceTicket st = ticketManager.grantServiceTicket(authentication.getTicketGrantingTicket(), spid);
                return "redirect:" + appendParamToUrl(sp.getHomeUrl(), Constants.PARAM_SERVICE_TICKET, st.getId());
            } else {
                return "redirect:/home";
            }
        }
        // 没有登录, 返回登录页面
        request.setAttribute("spid", spid);
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String submit(HttpServletRequest request, HttpServletResponse response,
                         @RequestParam Map<String, String> params) {
        Credential credential = credentialFactory.createCredential(params);
        Authentication authentication = authenticationManager.authenticate(credential);
        if (authentication != null && authentication.isSuccess()) {
            // todo reset sessionId
            request.getSession().invalidate();
            // set cookies
            Cookie cookie = new Cookie(tgcNameInCookie, authentication.getTicketGrantingTicket().getId());
            cookie.setPath(request.getContextPath());
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            // put principal and authentication into session
            request.getSession().setAttribute(authNameInSession, authentication);
            // check service provider, redirect to the serviceProvider's home url.
            String spid = params.get(spidNameInParams);
            if (spid != null) {
                ServiceProvider sp = serviceProviderRegistry.get(spid);
                if (sp != null && StringUtils.isNotBlank(sp.getHomeUrl())) {
                    ServiceTicket st = ticketManager.grantServiceTicket(authentication.getTicketGrantingTicket(), spid);
                    return "redirect:" + appendParamToUrl(sp.getHomeUrl(), Constants.PARAM_SERVICE_TICKET, st.getId());
                }
            }
            // if the spid is empty or invalid, then return the success page
            return "loginSuccess";
        } else {
            // put warning message into the ModelMap953720
            request.setAttribute("message", authentication.getMessage());
            return "login";
        }
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // 通知全部客户端登出
        // delete tgc from cookies
        webUtils.deleteCookieByName(response, tgcNameInCookie);
        // invalidate TGT
        webUtils.invalidateTGT(request);
        // invalidate session
        request.getSession().invalidate();
        // todo delete TGT
        // send logout urls
        // redirect to login page
        return "redirect:/login";
    }

    private String appendParamToUrl(String url, String name, String value) {
        if (url != null && name != null && value != null) {
            if (url.contains("?")) {
                url = url + "&" + name + "=" + value;
            } else {
                url = url + "?" + name + "=" + value;
            }
            return url;
        }
        return "";
    }

    public void setCredentialFactory(CredentialFactory credentialFactory) {
        this.credentialFactory = credentialFactory;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void setTicketManager(TicketManager ticketManager) {
        this.ticketManager = ticketManager;
    }

    public void setServiceProviderRegistry(ServiceProviderRegistry serviceProviderRegistry) {
        this.serviceProviderRegistry = serviceProviderRegistry;
    }

    public void setWebUtils(WebUtils webUtils) {
        this.webUtils = webUtils;
    }

    public void setAuthNameInSession(String authNameInSession) {
        this.authNameInSession = authNameInSession;
    }

    public void setTgcNameInCookie(String tgcNameInCookie) {
        this.tgcNameInCookie = tgcNameInCookie;
    }

    public void setSpidNameInParams(String spidNameInParams) {
        this.spidNameInParams = spidNameInParams;
    }
}
