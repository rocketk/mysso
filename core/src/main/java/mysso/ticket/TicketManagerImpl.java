package mysso.ticket;

import mysso.authentication.principal.Credential;
import mysso.serviceprovider.ServiceProvider;
import mysso.ticket.registry.TicketRegistry;
import mysso.util.UniqueIdGenerator;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
    private long livingTimeForServiceTicket = 20000;

    /**
     * living time for token in millisecond.
     * 5 minutes by default (5*60*1000=300000)
     */
    private long livingTimeForToken = 300000;

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
    public ServiceTicket grantServiceTicket(TicketGrantingTicket tgt, String spId) {
        Assert.notNull(tgt);
        Assert.notNull(spId);
        long now = System.currentTimeMillis();
        ServiceTicket st = new ServiceTicket(serviceTicketIdGenerator.getNewId(), now, tgt.getCredentialId(),
                tgt.getId(), spId, now + livingTimeForServiceTicket);
        if (tgt.getServiceTicketIds() == null) {
            tgt.setServiceTicketIds(new HashSet<String>());
        }
        tgt.getServiceTicketIds().add(st.getId());
        ticketRegistry.update(tgt);
        ticketRegistry.add(st);
        return st;
    }

    @Override
    public Token grantToken(ServiceTicket st) {
        long now = System.currentTimeMillis();
        Token tk = new Token(tokenIdGenerator.getNewId(), now, st.getCredentialId(),
                st.getTicketGrantingTicketId(), st.getServiceProviderId(), now + livingTimeForToken);
        TicketGrantingTicket tgt = ticketRegistry.get(st.getTicketGrantingTicketId(), TicketGrantingTicket.class);
        tgt.getTokenIds().add(tk.getId());
        st.markExpired();
        ticketRegistry.delete(st.getId(), ServiceTicket.class);
        ticketRegistry.add(tk);
        return tk;
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

    public long getLivingTimeForServiceTicket() {
        return livingTimeForServiceTicket;
    }

    public void setLivingTimeForServiceTicket(long livingTimeForServiceTicket) {
        this.livingTimeForServiceTicket = livingTimeForServiceTicket;
    }

    public long getLivingTimeForToken() {
        return livingTimeForToken;
    }

    public void setLivingTimeForToken(long livingTimeForToken) {
        this.livingTimeForToken = livingTimeForToken;
    }
}
