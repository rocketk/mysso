package mysso.web;

import mysso.authentication.Authentication;
import mysso.authentication.AuthenticationManager;
import mysso.authentication.credential.Credential;
import mysso.authentication.credential.CredentialFactory;
import mysso.serviceprovider.ServiceProvider;
import mysso.serviceprovider.registry.ServiceProviderRegistry;
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
    private ServiceProviderRegistry serviceProviderRegistry;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, @RequestParam Map<String, String> params) {
        String spid = params.get("spid");
        if (spid != null) {
            ServiceProvider sp = serviceProviderRegistry.get(spid);
            if (sp == null) {
                request.setAttribute("warning", "尚未接入的应用：" + spid);
                return "warning";
            } else {
                request.setAttribute("serviceProvider", sp);
            }
        }
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String submit(HttpServletRequest request, HttpServletResponse response,
                         @RequestParam Map<String, String> params) {
        Credential credential = credentialFactory.createCredential(params);
        Authentication authentication = authenticationManager.authenticate(credential);
        if (authentication != null && authentication.isSuccess()) {
            // set cookies, todo set cookie's path, secure
            response.addCookie(new Cookie("TGC", authentication.getTicketGrantingTicket().getId()));
            // put principle and authentication into session
            request.getSession().setAttribute("principal", authentication.getPrincipal());
            request.getSession().setAttribute("authentication", authentication);
            // todo reset sessionId
            // check service provider, redirect to the serviceProvider's home url.
            String spid = params.get("spid");
            if (spid != null) {
                ServiceProvider sp = serviceProviderRegistry.get(spid);
                if (sp != null && StringUtils.isNotBlank(sp.getHomeUrl())) {
                    return "redirect:" + sp.getHomeUrl();
                }
            }
            // if the spid is empty, then return the success page
            return "loginSuccess";
        } else {
            // put warning message into the ModelMap
            request.setAttribute("message", authentication.getMessage());
            return "login";
        }
    }

    public void setCredentialFactory(CredentialFactory credentialFactory) {
        this.credentialFactory = credentialFactory;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void setServiceProviderRegistry(ServiceProviderRegistry serviceProviderRegistry) {
        this.serviceProviderRegistry = serviceProviderRegistry;
    }
}
