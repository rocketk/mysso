package mysso.ticket.registry;

import mysso.ticket.AbstractTicket;
import mysso.ticket.ServiceTicket;
import mysso.ticket.TicketGrantingTicket;
import mysso.ticket.Token;
import mysso.ticket.exception.DuplicateIdException;
import mysso.ticket.exception.UnsupportTicketTypeException;
import org.apache.commons.collections4.SetUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by pengyu on 2017/8/13.
 */
public abstract class AbstractTicketRegistryTest {

    @Before
    public void setUp() {
        TicketRegistry ticketRegistry = getNewTicketRegistry();
        ticketRegistry.clear();
    }

    protected <T extends AbstractTicket> void verifyCRUD(T ticket1, T ticket2) {
        try {
            TicketRegistry ticketRegistry = getNewTicketRegistry();
            assertNotNull(ticketRegistry);
            // 验证添加ticket
            ticketRegistry.add(ticket1);
            assertNotNull(ticketRegistry.get(ticket1.getId(), ticket1.getClass()));
            assertEquals(ticket1, ticketRegistry.get(ticket1.getId(), ticket1.getClass()));
            assertNotNull(ticketRegistry.getAll(ticket1.getClass()));
            assertEquals(1, ticketRegistry.getAll(ticket1.getClass()).size());
            ticketRegistry.add(ticket2);
            assertNotNull(ticketRegistry.get(ticket2.getId(), ticket2.getClass()));
            assertEquals(ticket2, ticketRegistry.get(ticket2.getId(), ticket2.getClass()));
            assertEquals(2, ticketRegistry.getAll(ticket1.getClass()).size());
            // 验证更新ticket
            if (ticket1 instanceof TicketGrantingTicket) {
                // 验证tgt的集合属性更新
                Set<String> serviceTicketIds1 = new HashSet<>();
                serviceTicketIds1.add("st-001");
                serviceTicketIds1.add("st-002");
                ((TicketGrantingTicket) ticket1).setServiceTicketIds(serviceTicketIds1);
                ticketRegistry.update(ticket1);
                assertEquals(ticket1, ticketRegistry.get(ticket1.getId(), TicketGrantingTicket.class));
                Set<String> stIdsOfTicket1 = ((TicketGrantingTicket) ticket1).getServiceTicketIds();
                Set<String> stIdsOfRegistry = ticketRegistry.get(ticket1.getId(), TicketGrantingTicket.class).getServiceTicketIds();
                assertTrue(SetUtils.isEqualSet(stIdsOfTicket1, stIdsOfRegistry));
            } else if(ticket1 instanceof Token) {
                // 验证token的有效期更新
                ((Token) ticket1).setExpiredTime(System.currentTimeMillis() + 20000);
                ticketRegistry.update(ticket1);
                assertEquals(ticket1, ticketRegistry.get(ticket1.getId(), Token.class));
            }
            // 验证删除ticket
            assertTrue(ticketRegistry.delete(ticket1.getId(), ticket1.getClass()));
            assertNull(ticketRegistry.get(ticket1.getId(), ticket1.getClass()));
            assertNotNull(ticketRegistry.get(ticket2.getId(), ticket1.getClass()));
            assertEquals(1, ticketRegistry.getAll(ticket1.getClass()).size());
            assertTrue(ticketRegistry.delete(ticket2.getId(), ticket1.getClass()));
            assertNull(ticketRegistry.get(ticket2.getId(), ticket1.getClass()));
            assertEquals(0, ticketRegistry.getAll(ticket1.getClass()).size());
            // 验证registry中确实已经删除了ticket
            assertFalse(ticketRegistry.delete(ticket1.getId(), ticket1.getClass()));
            assertFalse(ticketRegistry.delete(ticket2.getId(), ticket1.getClass()));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void verifyTicketGrantingTicketCRUD() {
        TicketGrantingTicket tgt1 = new TicketGrantingTicket("tgt-001", System.currentTimeMillis(), "credentialId");
        TicketGrantingTicket tgt2 = new TicketGrantingTicket("tgt-002", System.currentTimeMillis(), "principal2");
        verifyCRUD(tgt1, tgt2);
    }

    @Test
    public void verifyServiceTicketCRUD() {
        long now = System.currentTimeMillis();
        ServiceTicket st1 = new ServiceTicket("st-001", now, "credentialId", "tgt-001", "sp-001", now + 10000);
        now = System.currentTimeMillis();
        ServiceTicket st2 = new ServiceTicket("st-002", now, "credentialId", "tgt-001", "sp-001", now + 10000);
        verifyCRUD(st1, st2);
    }

    @Test
    public void verifyTokenCRUD() {
        long now = System.currentTimeMillis();
        Token tk1 = new Token("tk-001", now, "credentialId", "tgt-001", "sp-001", now + 10000);
        now = System.currentTimeMillis();
        Token tk2 = new Token("tk-002", now, "credentialId", "tgt-001", "sp-001", now + 10000);
        verifyCRUD(tk1, tk2);
    }

    @Test
    public void verifyDeleteChildrenAfterDeletedTGT() {
        // create the ticket granting ticket
        TicketGrantingTicket tgt = new TicketGrantingTicket("tgt-001", System.currentTimeMillis(), "credentialId");

        // create the service tickets
        long now = System.currentTimeMillis();
        ServiceTicket st1 = new ServiceTicket("st-001", now, "credentialId", "tgt-001", "sp-001", now + 10000);
        now = System.currentTimeMillis();
        ServiceTicket st2 = new ServiceTicket("st-002", now, "credentialId", "tgt-001", "sp-001", now + 10000);
        Set<String> serviceTicketIds1 = new HashSet<>();
        serviceTicketIds1.add(st1.getId());
        serviceTicketIds1.add(st2.getId());
        tgt.setServiceTicketIds(serviceTicketIds1);
        // create the tokens
        now = System.currentTimeMillis();
        Token tk1 = new Token("tk-001", now, "credentialId", "tgt-001", "sp-001", now + 10000);
        now = System.currentTimeMillis();
        Token tk2 = new Token("tk-002", now, "credentialId", "tgt-001", "sp-001", now + 10000);
        Set<String> tokenIds = new HashSet<>();
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

    public void verifyUpdateTicketGrantingTicketAfterDeletedAbstractTicket() {
        // todo
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
        } catch (UnsupportTicketTypeException e) {
        }
        try {
            ticketRegistry.delete("any_id", unsupportedTicket.getClass());
            fail("an TicketException should be throw");
        } catch (UnsupportTicketTypeException e) {
        }
        try {
            ticketRegistry.get("any_id", unsupportedTicket.getClass());
            fail("an TicketException should be throw");
        } catch (UnsupportTicketTypeException e) {
        }
        try {
            ticketRegistry.getAll(unsupportedTicket.getClass());
            fail("an TicketException should be throw");
        } catch (UnsupportTicketTypeException e) {
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
        } catch (DuplicateIdException e) {
        }
    }

    @Test
    public void verifyClear() {
        TicketGrantingTicket tgt1 = new TicketGrantingTicket("tgt-001", System.currentTimeMillis(), "principal-001");
        // another tgt with the same id to tgt1
        TicketGrantingTicket tgt2 = new TicketGrantingTicket("tgt-002", System.currentTimeMillis(), "principal-001");
        TicketRegistry ticketRegistry = getNewTicketRegistry();
        ticketRegistry.add(tgt1);
        ticketRegistry.add(tgt2);
        assertEquals(2, ticketRegistry.getAll(TicketGrantingTicket.class).size());
        ticketRegistry.clear();
        assertEquals(0, ticketRegistry.getAll(TicketGrantingTicket.class).size());
    }

    protected abstract TicketRegistry getNewTicketRegistry();
}