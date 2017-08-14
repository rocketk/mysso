package mysso.ticket;

import mysso.authentication.principal.Credential;
import mysso.serviceprovider.ServiceProvider;
import mysso.ticket.exception.TicketException;
import mysso.ticket.registry.InMemoryTicketRegistry;
import mysso.ticket.registry.TicketRegistry;
import mysso.util.UUIDGenerator;
import mysso.util.UniqueIdGenerator;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by pengyu on 2017/8/14.
 */
public class TicketManagerImplTest {
    @Test
    public void verifyCreateAndDestoyTGT() {
        TicketManager manager = getNewCentralTicketManager();
        Credential credential = getNewCredential();
        try {
            TicketGrantingTicket tgt = manager.createTicketGrantingTicket(credential);
            assertNotNull(tgt);
            assertNotNull(tgt.getId());
            TicketGrantingTicket tgtFromRegistry = manager.getTicketGrantingTicket(tgt.getId());
            assertNotNull(tgtFromRegistry);
            assertEquals(tgt, tgtFromRegistry);
            manager.destroyTicketGrantingTicket(tgt.getId());
            assertNull(manager.getTicketGrantingTicket(tgt.getId()));
        } catch (TicketException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void verifyGrantingWithValidTGT() {
        TicketManager manager = getNewCentralTicketManager();
        Credential credential = getNewCredential();
        try {
            TicketGrantingTicket tgt = manager.createTicketGrantingTicket(credential);
            ServiceProvider sp = mock(ServiceProvider.class);
            when(sp.getId()).thenReturn("spid-example");
            // verify granting service ticket
            ServiceTicket st = manager.grantServiceTicket(tgt, sp);
            assertNotNull(st);
            assertNotNull(st.getId());
            assertEquals(tgt.getId(), st.getTicketGrantingTicketId());
            assertNotNull(tgt.getServiceTicketIds());
            assertEquals(1, tgt.getServiceTicketIds().size());
            assertEquals(st.getId(), tgt.getServiceTicketIds().get(0));
            assertEquals(st, manager.getServiceTicket(st.getId()));

            // verify granting token
            Token tk = manager.grantToken(st);
            assertNotNull(tk);
            assertNotNull(tk.getId());
            assertEquals(tk, manager.getToken(st.getId()));
            assertTrue(st.isExpired());
            assertNull(manager.getServiceTicket(st.getId()));
            assertEquals(tgt.getId(), tk.getTicketGrantingTicketId());
            assertEquals(tk, tgt.getTokenIds().get(0));

            manager.destroyTicketGrantingTicket(tgt.getId());
            assertNull(manager.getTicketGrantingTicket(tgt.getId()));
            assertNull(manager.getToken(tk.getId()));
        } catch (TicketException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void verifyGrantingWithInvalidTGT() {

    }

    @Test
    public void verifyGrantToken() {
    }

    @Test
    public void verifyValidateServiceTicket() {

    }

    @Test
    public void verifyValidateToken() {

    }

    @Test
    public void verifyDestroyTicketGrantingTicket() {
    }

    protected TicketManager getNewCentralTicketManager() {
        TicketRegistry ticketRegistry = new InMemoryTicketRegistry();
        UniqueIdGenerator tgtIdGenerator = new UUIDGenerator("tgt-", "");
        TicketManager ticketManager = new TicketManagerImpl(ticketRegistry, tgtIdGenerator);
        return ticketManager;
    }

    protected Credential getNewCredential() {
        Credential credential = new Credential() {
            @Override
            public String getId() {
                return "Jack";
            }
        };
        return credential;
    }

}