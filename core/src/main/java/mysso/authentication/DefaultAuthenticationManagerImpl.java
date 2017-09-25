package mysso.authentication;

import mysso.authentication.credential.Credential;
import mysso.authentication.exception.AuthenticationException;
import mysso.authentication.handler.AuthenticationHandler;
import mysso.authentication.handler.HandlerResult;
import mysso.authentication.principal.Principal;
import mysso.authentication.principal.PrincipalResolver;
import mysso.ticket.TicketGrantingTicket;
import mysso.ticket.TicketManager;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by pengyu.
 */
public class DefaultAuthenticationManagerImpl implements AuthenticationManager {
    private Logger log = LoggerFactory.getLogger(getClass());

    private AuthenticationHandler authenticationHandler;

    private PrincipalResolver principalResolver;

    private TicketManager ticketManager;

    @Override
    public Authentication authenticate(Credential credential) {
        Validate.notNull(credential, "credential is null");
        try {
            HandlerResult result = authenticationHandler.authenticate(credential);
            if (result.isSuccess()) {
                log.info("{} authenticated successfully", credential.getId());
                Principal principal = principalResolver.resolve(credential);
                TicketGrantingTicket ticketGrantingTicket = ticketManager.createTicketGrantingTicket(credential);
                log.info("TicketGrantingTicket created: {}, for principal {}", ticketGrantingTicket.getId(), principal.getId());
                return new Authentication(
                        principal,
                        new Date(),
                        ticketGrantingTicket,
                        new HashMap<String, Object>(),
                        true,
                        String.format("%s authenticated successfully", credential.getId()));
            } else {
                log.info("{} failed authenticating, caused by {}", credential.getId(), result.getMessage());
                return new Authentication(null, new Date(), null, null,
                        false, result.getMessage());
            }
        } catch (AuthenticationException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    public void setAuthenticationHandler(AuthenticationHandler authenticationHandler) {
        this.authenticationHandler = authenticationHandler;
    }

    public void setPrincipalResolver(PrincipalResolver principalResolver) {
        this.principalResolver = principalResolver;
    }

    public void setTicketManager(TicketManager ticketManager) {
        this.ticketManager = ticketManager;
    }
}
