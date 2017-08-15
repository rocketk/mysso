package mysso.ticket;

import mysso.authentication.principal.Credential;
import mysso.serviceprovider.ServiceProvider;
import mysso.ticket.registry.TicketRegistry;
import mysso.util.UniqueIdGenerator;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

/**
 * Created by pengyu on 2017/8/14.
 */
public class TicketManagerImpl implements TicketManager {
    @NotNull
    private TicketRegistry ticketRegistry;
    @NotNull
    private UniqueIdGenerator ticketGrantingTicketIdGenerator;
    @NotNull
    private UniqueIdGenerator serviceTicketIdGenerator;
    @NotNull
    private UniqueIdGenerator tokenIdGenerator;
    /**
     * living time for service ticket in millisecond.
     */
    private long livingTime = 20000;

    public TicketManagerImpl() {
    }

    public TicketManagerImpl(TicketRegistry ticketRegistry) {
        this.ticketRegistry = ticketRegistry;
    }

    public TicketManagerImpl(TicketRegistry ticketRegistry, UniqueIdGenerator ticketGrantingTicketIdGenerator,
                             UniqueIdGenerator serviceTicketIdGenerator, UniqueIdGenerator tokenIdGenerator) {
        this.ticketRegistry = ticketRegistry;
        this.ticketGrantingTicketIdGenerator = ticketGrantingTicketIdGenerator;
        this.serviceTicketIdGenerator = serviceTicketIdGenerator;
        this.tokenIdGenerator = tokenIdGenerator;
    }

    @Override
    public TicketGrantingTicket createTicketGrantingTicket(Credential credential) {
        TicketGrantingTicket ticketGrantingTicket = new TicketGrantingTicket(
                ticketGrantingTicketIdGenerator.getNewId(), System.currentTimeMillis(), credential.getId());
        ticketRegistry.add(ticketGrantingTicket);
        return ticketGrantingTicket;
    }

    @Override
    public ServiceTicket grantServiceTicket(TicketGrantingTicket tgt, ServiceProvider sp) {
        Assert.notNull(tgt);
        Assert.notNull(sp);
        long now = System.currentTimeMillis();
        ServiceTicket st = new ServiceTicket(serviceTicketIdGenerator.getNewId(), now, tgt.getCredentialId(),
                tgt.getId(), sp.getId(), now + livingTime);
        if (tgt.getServiceTicketIds() == null) {
            tgt.setServiceTicketIds(new ArrayList<String>());
        }
        tgt.getServiceTicketIds().add(st.getId());
        // todo save the tgt to registry
        ticketRegistry.add(st);
        return st;
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

    public void setTicketGrantingTicketIdGenerator(UniqueIdGenerator ticketGrantingTicketIdGenerator) {
        this.ticketGrantingTicketIdGenerator = ticketGrantingTicketIdGenerator;
    }

    public long getLivingTime() {
        return livingTime;
    }

    public void setLivingTime(long livingTime) {
        this.livingTime = livingTime;
    }
}
