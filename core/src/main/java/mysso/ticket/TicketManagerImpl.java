package mysso.ticket;


import mysso.authentication.credential.Credential;
import mysso.protocol1.Constants;
import mysso.ticket.registry.TicketRegistry;
import mysso.util.UniqueIdGenerator;
import org.apache.commons.lang3.Validate;

import java.util.HashSet;

/**
 * Created by pengyu on 2017/8/14.
 */
public class TicketManagerImpl implements TicketManager {

    private TicketRegistry ticketRegistry;

    private UniqueIdGenerator ticketGrantingTicketIdGenerator;

    private UniqueIdGenerator serviceTicketIdGenerator;

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
        Validate.notNull(tgt);
        Validate.notNull(spId);
        Validate.isTrue(!tgt.isExpired(), "tgt 已过期");
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
        Validate.notNull(st);
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
        Validate.notNull(stId);
        Validate.notNull(spId);
        ServiceTicket st = ticketRegistry.get(stId, ServiceTicket.class);
        if (st == null) {
            return new TicketValidateResult(Constants.INVALID_ST, "invalid service ticket", null);
        }
        if (!spId.equals(st.getServiceProviderId())) {
            return new TicketValidateResult(Constants.MISMATCH_SPID, "the given spid does not match the serviceTicket's spid", null);
        }
        if (st.isExpired()) {
            return new TicketValidateResult(Constants.EXPIRED_ST, "the st has been expired", null);
        } else {
            TicketGrantingTicket tgt = ticketRegistry.get(st.getTicketGrantingTicketId(), TicketGrantingTicket.class);
            if (tgt == null) {
                return new TicketValidateResult(Constants.EXPIRED_TGT, "the tgt has been expired", null);
            } else if (tgt.isExpired()) {
                return new TicketValidateResult(Constants.INVALID_TGT, "the tgt is invalid", null);
            }
        }
        return new TicketValidateResult(Constants.VALID_TICKET, "valid st", grantToken(st));
    }

    @Override
    public TicketValidateResult validateToken(String tkId, String spId) {
        Validate.notNull(tkId);
        Validate.notNull(spId);
        Token tk = ticketRegistry.get(tkId, Token.class);
        if (tk == null) {
            return new TicketValidateResult(Constants.INVALID_TK, "invalid token", null);
        }
        if (!spId.equals(tk.getServiceProviderId())) {
            return new TicketValidateResult(Constants.MISMATCH_SPID, "the given spid does not match the token's spid", null);
        }
        // tk存在，并且spid正确，校验tgt
        TicketGrantingTicket tgt = ticketRegistry.get(tk.getTicketGrantingTicketId(), TicketGrantingTicket.class);
        if (tgt == null) {
            return new TicketValidateResult(Constants.EXPIRED_TGT, "the tgt has been expired", null);
        } else if (tgt.isExpired()) {
            return new TicketValidateResult(Constants.INVALID_TGT, "the tgt is invalid", null);
        }
        // tgt正常，判断tk是否过期
        if (tk.isExpired()) {
            long now = System.currentTimeMillis();
            Token newToken = new Token(tokenIdGenerator.getNewId(), now, tk.getCredentialId(),
                    tk.getTicketGrantingTicketId(), tk.getServiceProviderId(), now + livingTimeForToken);
            ticketRegistry.delete(tk.getId(), Token.class);
            ticketRegistry.add(newToken);
            return new TicketValidateResult(Constants.VALID_BUT_EXPIRED, "token is valid but expired", newToken);
        }
        return new TicketValidateResult(Constants.VALID_TICKET, "valid token", tk);
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
