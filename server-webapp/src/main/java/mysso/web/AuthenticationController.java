package mysso.web;

import mysso.authentication.Authentication;
import mysso.authentication.AuthenticationManager;
import mysso.authentication.credential.Credential;
import mysso.authentication.credential.CredentialFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

/**
 * Created by pengyu on 2017/8/5.
 */
public class AuthenticationController {

    @NotNull
    private CredentialFactory credentialFactory;

    @NotNull
    private AuthenticationManager authenticationManager;


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request) {

        return "login.jsp";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String submit(HttpServletRequest request, ModelMap map) {
        Credential credential = credentialFactory.createCredential(map);
        Authentication authentication = authenticationManager.authenticate(credential);
        if (authentication != null && authentication.isSuccess()) {
            // set cookies
            // put principle into session
            // check service provider, redirect to the serviceProvider's home url.
            // if the spid is empty, then return the success page
            return "loginSuccess.jsp";
        } else {
            // put warning message into the ModelMap
            return "login.jsp";
        }
    }

    public void setCredentialFactory(CredentialFactory credentialFactory) {
        this.credentialFactory = credentialFactory;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
}
