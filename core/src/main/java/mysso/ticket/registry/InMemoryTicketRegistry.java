package mysso.ticket.registry;

import mysso.ticket.AbstractTicket;
import mysso.ticket.ServiceTicket;
import mysso.ticket.TicketGrantingTicket;
import mysso.ticket.Token;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pengyu on 2017/8/8.
 */
public class InMemoryTicketRegistry implements TicketRegistry {
    private Map<String, TicketGrantingTicket> tgtMap = new HashMap<>();
    private Map<String, ServiceTicket> stMap = new HashMap<>();
    private Map<String, Token> tkMap = new HashMap<>();
    @Override
    public void add(AbstractTicket ticket) {

    }

    @Override
    public boolean delete(String id, Class<? extends AbstractTicket> clazz) {
        return false;
    }

    @Override
    public <T extends AbstractTicket> T get(String id, Class<T> clazz) {
        return null;
    }

    @Override
    public <T extends AbstractTicket> Map<String, T> getAll(Class<T> clazz) {
        return null;
    }
}
