package mysso.ticket;


import mysso.authentication.credential.Credential;
import mysso.ticket.registry.TicketRegistry;
import mysso.util.Constants;
import mysso.util.UniqueIdGenerator;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.HashSet;

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
        Assert.isTrue(!tgt.isExpired(), "tgt 已过期");
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
        Assert.notNull(st);
        long now = System.currentTimeMillis();
        Token tk = new Token(tokenIdGenerator.getNewId(), now, st.getCredentialId(),
                st.getTicketGrantingTicketId(), st.getServiceProviderId(), now + livingTimeForToken);
        TicketGrantingTicket tgt = ticketRegistry.get(st.getTicketGrantingTicketId(), TicketGrantingTicket.class);
        tgt.getTokenIds().add(tk.getId());
        st.markExpired();
        ticketRegistry.delete(st.getId(), ServiceTicket.class);
        ticketRegistry.add(tk);
        ticketRegistry.update(tgt);
        return tk;
    }

    @Override
    public TicketValidateResult validateServiceTicket(String stId, String spId) {
        Assert.notNull(stId);
        Assert.notNull(spId);
        ServiceTicket st = ticketRegistry.get(stId, ServiceTicket.class);
        if (st == null) {
            return new TicketValidateResult(Constants.INVALID_ST, "invalid service ticket", null);
        }
        if (!spId.equals(st.getServiceProviderId())) {
            return new TicketValidateResult(Constants.MISMATCH_SPID, "the given spid does not match the serviceTicket's spid", null);
        }
        if (st.isExpired()) {
            return new TicketValidateResult(Constants.EXPIRED_ST, "the st has been expired", null);
        }
        return new TicketValidateResult(Constants.VALID_TICKET, "valid st", grantToken(st));
    }

    @Override
    public TicketValidateResult validateToken(String tkId, String spId) {
        Assert.notNull(tkId);
        Assert.notNull(spId);
        Token tk = ticketRegistry.get(tkId, Token.class);
        if (tk == null) {
            return new TicketValidateResult(Constants.INVALID_TK, "invalid token", null);
        }
        if (!spId.equals(tk.getServiceProviderId())) {
            return new TicketValidateResult(Constants.MISMATCH_SPID, "the given spid does not match the token's spid", null);
        }
        // tk存在, 并且未过期, 并且确实由指定的ServiceProvider所签发, 则校验成功
        if (tk.isExpired()) {
            long now = System.currentTimeMillis();
            Token newToken = new Token(tokenIdGenerator.getNewId(), now, tk.getCredentialId(),
                    tk.getTicketGrantingTicketId(), tk.getServiceProviderId(), now + livingTimeForToken);
            ticketRegistry.delete(tk.getId(), Token.class);
            ticketRegistry.add(newToken);
            return new TicketValidateResult(Constants.VALID_BUT_EXPIRED, "token is valid but expired", newToken);
        } else {
            return new TicketValidateResult(Constants.VALID_TICKET, "valid token", tk);
        }
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

    public void setServiceTicketIdGenerator(UniqueIdGenerator serviceTicketIdGenerator) {
        this.serviceTicketIdGenerator = serviceTicketIdGenerator;
    }

    public void setTokenIdGenerator(UniqueIdGenerator tokenIdGenerator) {
        this.tokenIdGenerator = tokenIdGenerator;
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
