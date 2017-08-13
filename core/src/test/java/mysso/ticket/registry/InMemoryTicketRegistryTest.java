package mysso.ticket.registry;

/**
 * Created by pengyu on 2017/8/13.
 */
public class InMemoryTicketRegistryTest extends AbstractTicketRegistryTest {
    @Override
    protected TicketRegistry getNewTicketRegistry() {
        return new InMemoryTicketRegistry();
    }
}
