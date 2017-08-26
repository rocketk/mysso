package mysso.web;

import mysso.authentication.principal.Principal;
import mysso.authentication.principal.PrincipalFactory;
import mysso.serviceprovider.ServiceProvider;
import mysso.serviceprovider.registry.ServiceProviderRegistry;
import mysso.ticket.TicketManager;
import mysso.ticket.TicketStatus;
import mysso.ticket.TicketValidateResult;
import mysso.ticket.Token;
import mysso.util.Constants;
import mysso.web.dto.Assertion;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by pengyu on 17-8-19.
 */
@Controller
public class ValidationController {
    private TicketManager ticketManager;
    private ServiceProviderRegistry serviceProviderRegistry;
    private PrincipalFactory principalFactory;

    private String spidNameInValidation;
    private String spkeyNameInValidation;
    private String stNameInValidation;
    private String tkNameInValidation;

    private Assertion validateServiceProvider(String spid, String secretKey) {
        // validate the spid
        if (StringUtils.isBlank(spid)) {
            return new Assertion(Constants.INVALID_SPID, "invalid spid");
        }
        if (StringUtils.isBlank(secretKey)) {
            return new Assertion(Constants.INVALID_SPKEY, "invalid secret key");
        }
        ServiceProvider serviceProvider = serviceProviderRegistry.get(spid);
        if (serviceProvider == null) {
            return new Assertion(Constants.INVALID_SPID, "invalid spid");
        }
        // validate the secret key of sp
        Assert.notNull(serviceProvider.getSecretKey(),
                "the secret key of serviceProvider is null, spid " + spid);
        if (!serviceProvider.getSecretKey().equals(spid)) {
            return new Assertion(Constants.INVALID_SPKEY, "invalid secret key");
        }
        return null;
    }

    @RequestMapping(value = "/validate/st", method = RequestMethod.POST)
    @ResponseBody
    public Assertion validateServiceTicket(HttpServletRequest request, HttpServletResponse response) {
        try {
            String spid = request.getParameter(spidNameInValidation);
            String secretKey = request.getParameter(spkeyNameInValidation);
            Assertion assertion = validateServiceProvider(spid, secretKey);
            if (assertion != null) {
                return assertion;
            }
            // validate the st
            String st = request.getParameter(stNameInValidation);
            if (StringUtils.isBlank(st)) {
                return new Assertion(Constants.INVALID_ST, "st is null or empty");
            }
            TicketValidateResult result = ticketManager.validateServiceTicket(st, spid);
            if (result.getCode() == Constants.VALID_TICKET) {
                // get a token if validated successfully
                Token token = result.getToken();
                assertion = new Assertion(Constants.VALID_TICKET, "valid st");
                assertion.setToken(token.getId());
                Principal principal = principalFactory.createPrincipal(token.getCredentialId());
                assertion.setPrincipal(principal);
                assertion.setExpiredTime(token.getExpiredTime());
                return assertion;
            }
            return new Assertion(result.getCode(), result.getMessage());
        } catch (Exception e) {
            return new Assertion(Constants.ERROR, "an error occurred");
        }
    }
    @RequestMapping(value = "/validate/tk", method = RequestMethod.POST)
    @ResponseBody
    public Assertion validateToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            String spid = request.getParameter(spidNameInValidation);
            String secretKey = request.getParameter(spkeyNameInValidation);
            Assertion assertion = validateServiceProvider(spid, secretKey);
            if (assertion != null) {
                return assertion;
            }
            // validate the st
            String tk = request.getParameter(tkNameInValidation);
            if (StringUtils.isBlank(tk)) {
                return new Assertion(Constants.INVALID_TK, "tk is null or empty");
            }
            TicketValidateResult result = ticketManager.validateToken(tk, spid);
            if (result.getCode() == Constants.VALID_BUT_EXPIRED) {
                // get a token if validated successfully
                Token token = result.getToken();
                assertion = new Assertion(Constants.VALID_TICKET, "valid tk");
                assertion.setToken(token.getId());
                Principal principal = principalFactory.createPrincipal(token.getCredentialId());
                assertion.setPrincipal(principal);
                assertion.setExpiredTime(token.getExpiredTime());
                return assertion;
            }
            return new Assertion(result.getCode(), result.getMessage());
        } catch (Exception e) {
            return new Assertion(Constants.ERROR, "an error occurred");
        }
    }

    public void setTicketManager(TicketManager ticketManager) {
        this.ticketManager = ticketManager;
    }

    public void setServiceProviderRegistry(ServiceProviderRegistry serviceProviderRegistry) {
        this.serviceProviderRegistry = serviceProviderRegistry;
    }

    public void setPrincipalFactory(PrincipalFactory principalFactory) {
        this.principalFactory = principalFactory;
    }

    public void setSpidNameInValidation(String spidNameInValidation) {
        this.spidNameInValidation = spidNameInValidation;
    }

    public void setSpkeyNameInValidation(String spkeyNameInValidation) {
        this.spkeyNameInValidation = spkeyNameInValidation;
    }

    public void setStNameInValidation(String stNameInValidation) {
        this.stNameInValidation = stNameInValidation;
    }

    public void setTkNameInValidation(String tkNameInValidation) {
        this.tkNameInValidation = tkNameInValidation;
    }
}
