package mysso.ticket.registry;

import mysso.ticket.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by pengyu on 2017/8/13.
 */
public abstract class AbstractTicketRegistryTest {

    protected <T extends AbstractTicket> void verifyCRUD(T ticket1, T ticket2) {
        try {
            TicketRegistry ticketRegistry = getNewTicketRegistry();
            assertNotNull(ticketRegistry);
            // verify add ticket
            ticketRegistry.add(ticket1);
            assertNotNull(ticketRegistry.get(ticket1.getId(), ticket1.getClass()));
            assertEquals(ticket1, ticketRegistry.get(ticket1.getId(), ticket1.getClass()));
            assertNotNull(ticketRegistry.getAll(ticket1.getClass()));
            assertEquals(1, ticketRegistry.getAll(ticket1.getClass()).size());
            ticketRegistry.add(ticket2);
            assertNotNull(ticketRegistry.get(ticket2.getId(), ticket1.getClass()));
            assertEquals(ticket2, ticketRegistry.get(ticket2.getId(), ticket1.getClass()));
            assertEquals(2, ticketRegistry.getAll(ticket1.getClass()).size());
            // verify delete ticket
            assertTrue(ticketRegistry.delete(ticket1.getId(), ticket1.getClass()));
            assertNull(ticketRegistry.get(ticket1.getId(), ticket1.getClass()));
            assertNotNull(ticketRegistry.get(ticket2.getId(), ticket1.getClass()));
            assertEquals(1, ticketRegistry.getAll(ticket1.getClass()).size());
            assertTrue(ticketRegistry.delete(ticket2.getId(), ticket1.getClass()));
            assertNull(ticketRegistry.get(ticket2.getId(), ticket1.getClass()));
            assertEquals(0, ticketRegistry.getAll(ticket1.getClass()).size());
            // verify delete ticket that does not exist
            assertFalse(ticketRegistry.delete(ticket1.getId(), ticket1.getClass()));
            assertFalse(ticketRegistry.delete(ticket2.getId(), ticket1.getClass()));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void verifyTicketGrantingTicketCRUD() {
        TicketGrantingTicket tgt1 = new TicketGrantingTicket("tgt-001", System.currentTimeMillis(), "principal1");
        List<String> serviceTicketIds1 = new ArrayList<>();
        serviceTicketIds1.add("st-001");
        serviceTicketIds1.add("st-002");
        tgt1.setServiceTicketIds(serviceTicketIds1);
        TicketGrantingTicket tgt2 = new TicketGrantingTicket("tgt-002", System.currentTimeMillis(), "principal2");
        List<String> serviceTicketIds2 = new ArrayList<>();
        serviceTicketIds1.add("st-011");
        serviceTicketIds1.add("st-012");
        tgt2.setServiceTicketIds(serviceTicketIds2);
        verifyCRUD(tgt1, tgt2);
    }

    @Test
    public void verifyServiceTicketCRUD() {
        long now = System.currentTimeMillis();
        ServiceTicket st1 = new ServiceTicket("st-001", now, "principal1", "tgt-001", "sp-001", now + 10000);
        now = System.currentTimeMillis();
        ServiceTicket st2 = new ServiceTicket("st-002", now, "principal1", "tgt-001", "sp-001", now + 10000);
        verifyCRUD(st1, st2);
    }

    @Test
    public void verifyTokenCRUD() {
        long now = System.currentTimeMillis();
        Token tk1 = new Token("tk-001", now, "principal1", "tgt-001", "sp-001", now + 10000);
        now = System.currentTimeMillis();
        Token tk2 = new Token("tk-002", now, "principal1", "tgt-001", "sp-001", now + 10000);
        verifyCRUD(tk1, tk2);
    }

    @Test
    public void verifyDeleteChildrenAfterDeletedTGT() {
        // create the ticket granting ticket
        TicketGrantingTicket tgt = new TicketGrantingTicket("tgt-001", System.currentTimeMillis(), "principal1");

        // create the service tickets
        long now = System.currentTimeMillis();
        ServiceTicket st1 = new ServiceTicket("st-001", now, "principal1", "tgt-001", "sp-001", now + 10000);
        now = System.currentTimeMillis();
        ServiceTicket st2 = new ServiceTicket("st-002", now, "principal1", "tgt-001", "sp-001", now + 10000);
        List<String> serviceTicketIds1 = new ArrayList<>();
        serviceTicketIds1.add(st1.getId());
        serviceTicketIds1.add(st2.getId());
        tgt.setServiceTicketIds(serviceTicketIds1);
        // create the tokens
        now = System.currentTimeMillis();
        Token tk1 = new Token("tk-001", now, "principal1", "tgt-001", "sp-001", now + 10000);
        now = System.currentTimeMillis();
        Token tk2 = new Token("tk-002", now, "principal1", "tgt-001", "sp-001", now + 10000);
        List<String> tokenIds = new ArrayList<>();
        tokenIds.add(tk1.getId());
        tokenIds.add(tk2.getId());
        tgt.setTokenIds(tokenIds);
        // save those tickets
        TicketRegistry ticketRegistry = getNewTicketRegistry();
        assertNotNull(ticketRegistry);
        ticketRegistry.add(tgt);
        ticketRegistry.add(st1);
        ticketRegistry.add(st2);
        ticketRegistry.add(tk1);
        ticketRegistry.add(tk2);
        assertEquals(1, ticketRegistry.getAll(TicketGrantingTicket.class).size());
        assertEquals(2, ticketRegistry.getAll(ServiceTicket.class).size());
        assertEquals(2, ticketRegistry.getAll(Token.class).size());
        // verify delete children after deleted tgt
        assertTrue(ticketRegistry.delete(tgt.getId(), TicketGrantingTicket.class));
        assertEquals(0, ticketRegistry.getAll(TicketGrantingTicket.class).size());
        assertEquals(0, ticketRegistry.getAll(ServiceTicket.class).size());
        assertEquals(0, ticketRegistry.getAll(Token.class).size());
    }

    @Test
    public void verifyUnsupportedClass() {
        TicketRegistry ticketRegistry = getNewTicketRegistry();
        AbstractTicket unsupportedTicket = new AbstractTicket() {
            @Override
            public boolean isExpired() {
                return false;
            }
        };
        try {
            ticketRegistry.add(unsupportedTicket);
            fail("an TicketException should be throw");
        } catch (TicketException e) {
            assertEquals(String.format(TicketException.UNSUPPORTED_TICKET_TYPE, unsupportedTicket.getClass()), e.getMessage());
        }
        try {
            ticketRegistry.delete("any_id", unsupportedTicket.getClass());
            fail("an TicketException should be throw");
        } catch (TicketException e) {
            assertEquals(String.format(TicketException.UNSUPPORTED_TICKET_TYPE, unsupportedTicket.getClass()), e.getMessage());
        }
        try {
            ticketRegistry.get("any_id", unsupportedTicket.getClass());
            fail("an TicketException should be throw");
        } catch (TicketException e) {
            assertEquals(String.format(TicketException.UNSUPPORTED_TICKET_TYPE, unsupportedTicket.getClass()), e.getMessage());
        }
        try {
            ticketRegistry.getAll(unsupportedTicket.getClass());
            fail("an TicketException should be throw");
        } catch (TicketException e) {
            assertEquals(String.format(TicketException.UNSUPPORTED_TICKET_TYPE, unsupportedTicket.getClass()), e.getMessage());
        }

    }

    @Test
    public void verifyAddWithDuplicateId() {
        TicketGrantingTicket tgt1 = new TicketGrantingTicket("tgt-001", System.currentTimeMillis(), "principal-001");
        // another tgt with the same id to tgt1
        TicketGrantingTicket tgt2 = new TicketGrantingTicket("tgt-001", System.currentTimeMillis(), "principal-001");
        TicketRegistry ticketRegistry = getNewTicketRegistry();
        ticketRegistry.add(tgt1);
        // add tgt2
        try {
            ticketRegistry.add(tgt2);
            fail("an TicketException should be throw");
        } catch (TicketException e) {
            assertEquals(String.format(TicketException.DUPLICATED_ID, tgt2.getId()), e.getMessage());
        }
    }

    protected abstract TicketRegistry getNewTicketRegistry();
}