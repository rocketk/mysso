package mysso.web;

import mysso.authentication.principal.Principal;
import mysso.authentication.principal.PrincipalResolver;
import mysso.protocol1.Constants;
import mysso.protocol1.dto.AssertionDto;
import mysso.protocol1.dto.PrincipalDto;
import mysso.security.SecretPasscodeValidator;
import mysso.serviceprovider.ServiceProvider;
import mysso.serviceprovider.registry.ServiceProviderRegistry;
import mysso.ticket.TicketManager;
import mysso.ticket.TicketValidateResult;
import mysso.ticket.Token;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
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
    private Logger log = LoggerFactory.getLogger(getClass());
    private TicketManager ticketManager;
    private ServiceProviderRegistry serviceProviderRegistry;
    private PrincipalResolver principalResolver;
    private SecretPasscodeValidator secretPasscodeValidator;


    private AssertionDto validateServiceProvider(String spid, String passcode) {
        // validate the spid
        if (StringUtils.isBlank(spid)) {
            return new AssertionDto(Constants.INVALID_SPID, "invalid spid");
        }
        if (StringUtils.isBlank(passcode)) {
            return new AssertionDto(Constants.INVALID_PASSCODE, "passcode is null");
        }
        ServiceProvider serviceProvider = serviceProviderRegistry.get(spid);
        if (serviceProvider == null) {
            return new AssertionDto(Constants.INVALID_SPID, "invalid spid");
        }
        // validate the secret key of sp
        Validate.notNull(serviceProvider.getSecretKey(),
                "the secret key of serviceProvider is null, spid " + spid);
//        if (!serviceProvider.getSecretKey().equals(passcode)) {
        if (!secretPasscodeValidator.validateSecretPasscode(serviceProvider.getSecretKey(), passcode)) {
            return new AssertionDto(Constants.INVALID_PASSCODE, "invalid secret passcode");
        }
        return null;
    }

    @RequestMapping(value = "/validate/st", method = RequestMethod.POST)
    @ResponseBody
    public AssertionDto validateServiceTicket(HttpServletRequest request, HttpServletResponse response) {
        try {
            String spid = request.getParameter(Constants.PARAM_SPID);
            String passcode = request.getParameter(Constants.PARAM_SECRET_PASSCODE);
            AssertionDto assertion = validateServiceProvider(spid, passcode);
            if (assertion != null) {
                log.info("validated service ticket failed, assertion.code: {}, spid: {}", assertion.getCode(), spid);
                return assertion;
            }
            // validate the st
            String st = request.getParameter(Constants.PARAM_SERVICE_TICKET);
            log.info("validating service ticket, st: {}, spid: {}", st, spid);
            if (StringUtils.isBlank(st)) {
                return new AssertionDto(Constants.INVALID_ST, "st is null or empty");
            }
            TicketValidateResult result = ticketManager.validateServiceTicket(st, spid);
            if (result.getCode() == Constants.VALID_TICKET) {
                log.info("validated service ticket successfully, st: {}, spid: {}", st, spid);
                // get a token if validated successfully
                Token token = result.getToken();
                assertion = new AssertionDto(Constants.VALID_TICKET, "valid st");
                assertion.setToken(token.getId());
                Principal principal = principalResolver.resolve(token.getCredentialId());
                assertion.setPrincipal(new PrincipalDto(principal.getId(), principal.getAttributes()));
                assertion.setExpiredTime(token.getExpiredTime());
                return assertion;
            }
            log.info("validated service ticket failed, code: {}st: {}, spid: {}", result.getCode(), st, spid);
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
            String secretKey = request.getParameter(Constants.PARAM_SECRET_PASSCODE);
            AssertionDto assertion = validateServiceProvider(spid, secretKey);
            if (assertion != null) {
                log.info("validated token failed, assertion.code: {}, spid: {}", assertion.getCode(), spid);
                return assertion;
            }
            // validate the tk
            String tk = request.getParameter(Constants.PARAM_TOKEN);
            log.info("validating token, tk: {}, spid: {}", tk, spid);
            if (StringUtils.isBlank(tk)) {
                return new AssertionDto(Constants.INVALID_TK, "tk is null or empty");
            }
            TicketValidateResult result = ticketManager.validateToken(tk, spid);
            if (result.getCode() == Constants.VALID_BUT_EXPIRED) {
                log.info("validated token successfully, the token is expired, tk: {}, spid: {}", tk, spid);
                // get a token if validated successfully
                Token token = result.getToken();
                assertion = new AssertionDto(Constants.VALID_TICKET, "valid tk");
                assertion.setToken(token.getId());
                Principal principal = principalResolver.resolve(token.getCredentialId());
                assertion.setPrincipal(new PrincipalDto(principal.getId(), principal.getAttributes()));
                assertion.setExpiredTime(token.getExpiredTime());
                return assertion;
            }
            if (result.getCode() == Constants.VALID_TICKET) {
                log.info("validated token successfully, code: {}, st: {}, spid: {}", result.getCode(), tk, spid);
            } else {
                log.info("validated token failed, code: {}st: {}, spid: {}", result.getCode(), tk, spid);
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

    public void setSecretPasscodeValidator(SecretPasscodeValidator secretPasscodeValidator) {
        this.secretPasscodeValidator = secretPasscodeValidator;
    }
}
