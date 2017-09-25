package mysso.ticket.registry;

/**
 * Created by pengyu.
 */
public class InMemoryTicketRegistryTest extends AbstractTicketRegistryTest {
    @Override
    protected TicketRegistry getNewTicketRegistry() {
        return new InMemoryTicketRegistry();
    }
}
