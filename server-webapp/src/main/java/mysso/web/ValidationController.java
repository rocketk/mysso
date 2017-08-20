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

    @RequestMapping(value = "/validate/st", method = RequestMethod.POST)
    @ResponseBody
    public Assertion validateServiceTicket(HttpServletRequest request, HttpServletResponse response) {
        try {
            // validate the spid
            String spid = request.getParameter(spidNameInValidation);
            if (StringUtils.isBlank(spid)) {
                return new Assertion(Constants.INVALID_SPID, "invalid spid");
            }
            String secretKey = request.getParameter(spkeyNameInValidation);
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
            // validate the st
            String st = request.getParameter(stNameInValidation);
            if (StringUtils.isBlank(st)) {
                return new Assertion(Constants.INVALID_ST, "invalid st");
            }
            TicketValidateResult result = ticketManager.validateServiceTicket(st, spid);
            if (result.getStatus().equals(TicketStatus.INVALID)) {
                return new Assertion(Constants.INVALID_ST, "invalid st");
            }
            // get a token if validated successfully
            Token token = result.getToken();
            Assertion assertion = new Assertion(Constants.VALID_TICKET, "valid st");
            assertion.setToken(token.getId());
            Principal principal = principalFactory.createPrincipal(token.getCredentialId());
            assertion.setPrincipal(principal);
            assertion.setExpiredTime(token.getExpiredTime());
            return assertion;
        } catch (Throwable t) {
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
