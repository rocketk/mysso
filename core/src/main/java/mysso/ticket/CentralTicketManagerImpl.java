package mysso.ticket;

import mysso.authentication.principal.Credential;
import mysso.ticket.registry.TicketRegistry;

import javax.validation.constraints.NotNull;

/**
 * Created by pengyu on 2017/8/14.
 */
public class CentralTicketManagerImpl implements CentralTicketManager {
    @NotNull
    private TicketRegistry ticketRegistry;

    public CentralTicketManagerImpl(TicketRegistry ticketRegistry) {
        this.ticketRegistry = ticketRegistry;
    }

    @Override
    public TicketGrantingTicket createTicketGrantingTicket(Credential credential) {
        return null;
    }

    @Override
    public ServiceTicket grantServiceTicket(String tgtId, String spId) {
        return null;
    }

    @Override
    public Token grantToken(ServiceTicket st) {
        return null;
    }

    @Override
    public TicketGrantingTicket getTicketGrantingTicket(String id) {
        return ticketRegistry.get(id, TicketGrantingTicket.class);
    }

    @Override
    public ServiceTicket getServiceTicket(String id) {
        return ticketRegistry.get(id, ServiceTicket.class);
    }

    @Override
    public Token getToken(String id) {
        return ticketRegistry.get(id, Token.class);
    }

    @Override
    public void destroyTicketGrantingTicket(String id) {
        ticketRegistry.delete(id, TicketGrantingTicket.class);
    }
}
