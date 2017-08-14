package mysso.ticket;

import mysso.ticket.registry.InMemoryTicketRegistry;
import mysso.ticket.registry.TicketRegistry;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by pengyu on 2017/8/14.
 */
public class CentralTicketManagerImplTest {
    @Test
    public void verifyCreateTicketGrantingTicket() throws Exception {
        CentralTicketManager manager = getNewCentralTicketManager();
        TicketGrantingTicket tgt = null;
        
    }

    @Test
    public void verifyGrantServiceTicket() throws Exception {
    }

    @Test
    public void verifyGrantToken() throws Exception {
    }

    @Test
    public void verifyGetTicketGrantingTicket() throws Exception {
    }

    @Test
    public void verifyGetServiceTicket() throws Exception {
    }

    @Test
    public void verifyGetToken() throws Exception {
    }

    @Test
    public void verifyDestroyTicketGrantingTicket() throws Exception {
    }

    protected CentralTicketManager getNewCentralTicketManager() {
        TicketRegistry ticketRegistry = new InMemoryTicketRegistry();
        CentralTicketManager centralTicketManager = new CentralTicketManagerImpl(ticketRegistry);
        return centralTicketManager;
    }
}