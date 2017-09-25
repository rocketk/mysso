package mysso.ticket;

import mysso.authentication.credential.Credential;
import mysso.protocol1.Constants;
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
 * Created by pengyu.
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
    public void verifyGrantServiceTicketAndTokenWithValidTGT() {
        TicketManager manager = getNewCentralTicketManager();
        Credential credential = getNewCredential();
        try {
            TicketGrantingTicket tgt = manager.createTicketGrantingTicket(credential);
            ServiceProvider sp = mock(ServiceProvider.class);
            when(sp.getId()).thenReturn("spid-example");
            // verify granting service ticket
            ServiceTicket st = manager.grantServiceTicket(tgt, sp.getId());
            assertNotNull(st);
            assertNotNull(st.getId());
            assertEquals(tgt.getId(), st.getTicketGrantingTicketId());
            assertNotNull(tgt.getServiceTicketIds());
            assertEquals(1, tgt.getServiceTicketIds().size());
            // 验证tgt.serviceTicketIds包含了新签发的serviceTicketId
            assertTrue("tgt.serviceTicketIds should contains st.id", tgt.getServiceTicketIds().contains(st.getId()));
            // 验证st已经保存到registry中
            assertEquals("st未被保存到registry中", st, manager.getServiceTicket(st.getId()));
            // 验证tgt已经保存到registry中
            assertEquals("tgt未被保存到registry中", tgt, manager.getTicketGrantingTicket(tgt.getId()));

            // verify granting token
            Token tk = manager.grantToken(st);
            assertNotNull(tk);
            assertNotNull(tk.getId());
            assertEquals(tk, manager.getToken(tk.getId()));
            assertTrue("st.isExpired() should be true", st.isExpired());
            assertNull(manager.getServiceTicket(st.getId()));
            assertEquals(tgt.getId(), tk.getTicketGrantingTicketId());
            // 验证tgt.tokenIds包含了新签发的tokenId
            assertTrue("tk.id should be contained in tgt.tokenIds", tgt.getTokenIds().contains(tk.getId()));
            // 验证tgt已经保存到registry中
            assertEquals("tgt未被保存到registry中", tgt, manager.getTicketGrantingTicket(tgt.getId()));

            manager.destroyTicketGrantingTicket(tgt.getId());
            assertNull(manager.getTicketGrantingTicket(tgt.getId()));
            assertNull(manager.getToken(tk.getId()));
        } catch (TicketException e){
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void verifyGrantServiceTicketWithInvalidTGT() {
        TicketManager manager = getNewCentralTicketManager();
        Credential credential = getNewCredential();
        TicketGrantingTicket tgt = manager.createTicketGrantingTicket(credential);
        // 设置为过期
        tgt.markExpired();
        assertTrue("tgt 应已过期", tgt.isExpired());
        ServiceProvider sp = mock(ServiceProvider.class);
        when(sp.getId()).thenReturn("spid-example");
        ServiceTicket st = manager.grantServiceTicket(tgt, sp.getId());
    }

    @Test
    public void verifyValidateGoodServiceTicket() {
        TicketManager manager = getNewCentralTicketManager();
        Credential credential = mock(Credential.class);
        when(credential.getId()).thenReturn("credentialId");
        TicketGrantingTicket tgt = manager.createTicketGrantingTicket(credential);
        String spId = "sp-001";
        ServiceTicket st = manager.grantServiceTicket(tgt, spId);
        assertEquals("st应当校验通过", Constants.VALID_TICKET,
                manager.validateServiceTicket(st.getId(), spId).getCode());
    }
    @Test
    public void verifyValidateExpiredServiceTicket() {
        TicketManager manager = getNewCentralTicketManager();
        Credential credential = mock(Credential.class);
        when(credential.getId()).thenReturn("credentialId");
        TicketGrantingTicket tgt = manager.createTicketGrantingTicket(credential);
        String spId = "sp-001";
        ServiceTicket st = manager.grantServiceTicket(tgt, spId);
        st.markExpired();
        assertEquals("st应当校验失败, 因为它已经失效了", Constants.EXPIRED_ST,
                manager.validateServiceTicket(st.getId(), spId).getCode());
    }
    @Test
    public void verifyValidateDestroyedServiceTicket() {
        TicketManager manager = getNewCentralTicketManager();
        Credential credential = mock(Credential.class);
        when(credential.getId()).thenReturn("credentialId");
        TicketGrantingTicket tgt = manager.createTicketGrantingTicket(credential);
        String spId = "sp-001";
        ServiceTicket st = manager.grantServiceTicket(tgt, spId);
        manager.destroyTicketGrantingTicket(tgt.getId());
        assertEquals("st应当校验失败, 因为它已经被销毁了", Constants.INVALID_ST,
                manager.validateServiceTicket(st.getId(), spId).getCode());
    }
    @Test
    public void verifyValidateServiceTicketWithInvalidServiceProvider() {
        TicketManager manager = getNewCentralTicketManager();
        Credential credential = mock(Credential.class);
        when(credential.getId()).thenReturn("credentialId");
        TicketGrantingTicket tgt = manager.createTicketGrantingTicket(credential);
        String spId = "sp-001";
        ServiceTicket st = manager.grantServiceTicket(tgt, spId);
        assertEquals("st应当校验失败, 因为它所包含的ServiceProvider与所给定的不一致", Constants.MISMATCH_SPID,
                manager.validateServiceTicket(st.getId(), "sp-002").getCode());
    }

    protected TicketManager getNewCentralTicketManager() {
        TicketRegistry ticketRegistry = new InMemoryTicketRegistry();
        UniqueIdGenerator tgtIdGenerator = new UUIDGenerator("tgt-", "");
        UniqueIdGenerator stIdGenerator = new UUIDGenerator("st-", "");
        UniqueIdGenerator tkIdGenerator = new UUIDGenerator("tk-", "");
        TicketManager ticketManager = new TicketManagerImpl(ticketRegistry, tgtIdGenerator, stIdGenerator, tkIdGenerator);
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