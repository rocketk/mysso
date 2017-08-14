package mysso.ticket;

import mysso.authentication.principal.Credential;
import mysso.serviceprovider.ServiceProvider;
import mysso.ticket.registry.TicketRegistry;
import mysso.util.UniqueIdGenerator;

import javax.validation.constraints.NotNull;

/**
 * Created by pengyu on 2017/8/14.
 */
public class CentralTicketManagerImpl implements CentralTicketManager {
    @NotNull
    private TicketRegistry ticketRegistry;
    @NotNull
    private UniqueIdGenerator uniqueIdGenerator;

    public CentralTicketManagerImpl() {
    }

    public CentralTicketManagerImpl(TicketRegistry ticketRegistry) {
        this.ticketRegistry = ticketRegistry;
    }

    public CentralTicketManagerImpl(TicketRegistry ticketRegistry, UniqueIdGenerator uniqueIdGenerator) {
        this.ticketRegistry = ticketRegistry;
        this.uniqueIdGenerator = uniqueIdGenerator;
    }

    @Override
    public TicketGrantingTicket createTicketGrantingTicket(Credential credential) {
        TicketGrantingTicket ticketGrantingTicket = new TicketGrantingTicket();
        return null;
    }

    @Override
    public ServiceTicket grantServiceTicket(TicketGrantingTicket tgt, ServiceProvider sp) {
        return null;
    }

    @Override
    public Token grantToken(ServiceTicket st) {
        return null;
    }

    @Override
    public boolean validateServiceTicket(ServiceTicket st) {
        return false;
    }

    @Override
    public boolean validateToken(Token tk) {
        return false;
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

    public void setTicketRegistry(TicketRegistry ticketRegistry) {
        this.ticketRegistry = ticketRegistry;
    }

    public void setUniqueIdGenerator(UniqueIdGenerator uniqueIdGenerator) {
        this.uniqueIdGenerator = uniqueIdGenerator;
    }
}
