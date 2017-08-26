package mysso.web;

import mysso.authentication.principal.Principal;
import mysso.authentication.principal.PrincipalResolver;
import mysso.protocol1.Constants;
import mysso.protocol1.dto.PrincipalDto;
import mysso.serviceprovider.ServiceProvider;
import mysso.serviceprovider.registry.ServiceProviderRegistry;
import mysso.ticket.TicketManager;
import mysso.ticket.TicketValidateResult;
import mysso.ticket.Token;
import mysso.protocol1.dto.AssertionDto;
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
    private PrincipalResolver principalResolver;



    private AssertionDto validateServiceProvider(String spid, String secretKey) {
        // validate the spid
        if (StringUtils.isBlank(spid)) {
            return new AssertionDto(Constants.INVALID_SPID, "invalid spid");
        }
        if (StringUtils.isBlank(secretKey)) {
            return new AssertionDto(Constants.INVALID_SPKEY, "invalid secret key");
        }
        ServiceProvider serviceProvider = serviceProviderRegistry.get(spid);
        if (serviceProvider == null) {
            return new AssertionDto(Constants.INVALID_SPID, "invalid spid");
        }
        // validate the secret key of sp
        Assert.notNull(serviceProvider.getSecretKey(),
                "the secret key of serviceProvider is null, spid " + spid);
        if (!serviceProvider.getSecretKey().equals(spid)) {
            return new AssertionDto(Constants.INVALID_SPKEY, "invalid secret key");
        }
        return null;
    }

    @RequestMapping(value = "/validate/st", method = RequestMethod.POST)
    @ResponseBody
    public AssertionDto validateServiceTicket(HttpServletRequest request, HttpServletResponse response) {
        try {
            String spid = request.getParameter(Constants.PARAM_SPID);
            String secretKey = request.getParameter(Constants.PARAM_SPKEY);
            AssertionDto assertion = validateServiceProvider(spid, secretKey);
            if (assertion != null) {
                return assertion;
            }
            // validate the st
            String st = request.getParameter(Constants.PARAM_SERVICE_TICKET);
            if (StringUtils.isBlank(st)) {
                return new AssertionDto(Constants.INVALID_ST, "st is null or empty");
            }
            TicketValidateResult result = ticketManager.validateServiceTicket(st, spid);
            if (result.getCode() == Constants.VALID_TICKET) {
                // get a token if validated successfully
                Token token = result.getToken();
                assertion = new AssertionDto(Constants.VALID_TICKET, "valid st");
                assertion.setToken(token.getId());
                Principal principal = principalResolver.resolve(token.getCredentialId());
                assertion.setPrincipal(new PrincipalDto(principal.getId(), principal.getAttributes()));
                assertion.setExpiredTime(token.getExpiredTime());
                return assertion;
            }
            return new AssertionDto(result.getCode(), result.getMessage());
        } catch (Exception e) {
            return new AssertionDto(Constants.ERROR, "an error occurred");
        }
    }
    @RequestMapping(value = "/validate/tk", method = RequestMethod.POST)
    @ResponseBody
    public AssertionDto validateToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            String spid = request.getParameter(Constants.PARAM_SPID);
            String secretKey = request.getParameter(Constants.PARAM_SPKEY);
            AssertionDto assertion = validateServiceProvider(spid, secretKey);
            if (assertion != null) {
                return assertion;
            }
            // validate the st
            String tk = request.getParameter(Constants.PARAM_TOKEN);
            if (StringUtils.isBlank(tk)) {
                return new AssertionDto(Constants.INVALID_TK, "tk is null or empty");
            }
            TicketValidateResult result = ticketManager.validateToken(tk, spid);
            if (result.getCode() == Constants.VALID_BUT_EXPIRED) {
                // get a token if validated successfully
                Token token = result.getToken();
                assertion = new AssertionDto(Constants.VALID_TICKET, "valid tk");
                assertion.setToken(token.getId());
                Principal principal = principalResolver.resolve(token.getCredentialId());
                assertion.setPrincipal(new PrincipalDto(principal.getId(), principal.getAttributes()));
                assertion.setExpiredTime(token.getExpiredTime());
                return assertion;
            }
            return new AssertionDto(result.getCode(), result.getMessage());
        } catch (Exception e) {
            return new AssertionDto(Constants.ERROR, "an error occurred");
        }
    }

    public void setTicketManager(TicketManager ticketManager) {
        this.ticketManager = ticketManager;
    }

    public void setServiceProviderRegistry(ServiceProviderRegistry serviceProviderRegistry) {
        this.serviceProviderRegistry = serviceProviderRegistry;
    }

    public void setPrincipalResolver(PrincipalResolver principalResolver) {
        this.principalResolver = principalResolver;
    }
}
