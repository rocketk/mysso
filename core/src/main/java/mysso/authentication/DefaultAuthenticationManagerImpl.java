package mysso.authentication;

import mysso.authentication.exception.AuthenticationException;
import mysso.ticket.CentralTicketManager;
import mysso.authentication.handler.AuthenticationHandler;
import mysso.authentication.principal.Credential;
import mysso.authentication.principal.Principal;
import mysso.authentication.principal.PrincipalResolver;
import mysso.ticket.ServiceTicket;
import mysso.ticket.TicketGrantingTicket;
import mysso.ticket.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by pengyu on 2017/8/5.
 */
public class DefaultAuthenticationManagerImpl implements AuthenticationManager {
    private Logger log = LoggerFactory.getLogger(getClass());
    @NotNull
    private AuthenticationHandler authenticationHandler;

    @NotNull
    private PrincipalResolver principalResolver;

    @NotNull
    private CentralTicketManager centralTicketManager;

    @Override
    public Authentication authenticate(Credential credential) {
        try {
            if (authenticationHandler.authenticate(credential)) {
                log.info("{} successfully authenticated", credential.getId());
                Principal principal = principalResolver.resolve(credential);
                TicketGrantingTicket ticketGrantingTicket = centralTicketManager.createTicketGrantingTicket(credential);
                log.info("TicketGrantingTicket created: {}, for principal {}", ticketGrantingTicket.getId(), principal.getId());
                return new Authentication(
                        principal,
                        new Date(),
                        ticketGrantingTicket,
                        new HashMap<String, Object>(),
                        true,
                        String.format("%s successfully authenticated", credential.getId()));
            } else {
                throw new AuthenticationException();
            }
        } catch (AuthenticationException e) {
            return new Authentication(null, new Date(), null, null,
                    true, String.format("%s failed authenticating, caused by: %s", credential.getId(), e.getMessage()));
        }
    }

    @Override
    public Assertion validateServiceTicket(ServiceTicket serviceTicket) {
        return null;
    }

    @Override
    public Assertion validateToken(Token token) {
        return null;
    }

    public void setAuthenticationHandler(AuthenticationHandler authenticationHandler) {
        this.authenticationHandler = authenticationHandler;
    }

    public void setPrincipalResolver(PrincipalResolver principalResolver) {
        this.principalResolver = principalResolver;
    }

    public void setCentralTicketManager(CentralTicketManager centralTicketManager) {
        this.centralTicketManager = centralTicketManager;
    }
}
