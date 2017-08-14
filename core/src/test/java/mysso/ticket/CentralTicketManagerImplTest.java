package mysso.ticket;

import mysso.authentication.principal.Credential;
import mysso.ticket.exception.TicketException;
import mysso.ticket.registry.InMemoryTicketRegistry;
import mysso.ticket.registry.TicketRegistry;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by pengyu on 2017/8/14.
 */
public class CentralTicketManagerImplTest {
    @Test
    public void verifyCreateTicketGrantingTicket() {
        CentralTicketManager manager = getNewCentralTicketManager();
        Credential credential = getNewCredential();
        try {
            TicketGrantingTicket tgt = manager.createTicketGrantingTicket(credential);
            assertNotNull(tgt);
            assertNotNull(tgt.getId());
            manager.destroyTicketGrantingTicket(tgt.getId());
        } catch (TicketException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void verifyGrantServiceTicket() {
    }

    @Test
    public void verifyGrantToken() {
    }

    @Test
    public void verifyGetTicketGrantingTicket() {
    }

    @Test
    public void verifyGetServiceTicket() {
    }

    @Test
    public void verifyGetToken() {
    }

    @Test
    public void verifyDestroyTicketGrantingTicket() {
    }

    protected CentralTicketManager getNewCentralTicketManager() {
        TicketRegistry ticketRegistry = new InMemoryTicketRegistry();
        CentralTicketManager centralTicketManager = new CentralTicketManagerImpl(ticketRegistry);
        return centralTicketManager;
    }

    protected Credential getNewCredential() {
        Credential credential = new Credential() {
            @Override
            public String getId() {
                return UUID.randomUUID().toString();
            }
        };
        return credential;
    }
}