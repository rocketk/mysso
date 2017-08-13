package mysso.ticket.registry;

import mysso.ticket.AbstractTicket;

import java.util.Map;

/**
 * Created by pengyu on 2017/8/6.
 */
public interface TicketRegistry {
    void add(AbstractTicket ticket);

    boolean delete(String id, Class<? extends AbstractTicket> clazz);

    <T extends AbstractTicket> T get(String id, Class<T> clazz);

    <T extends AbstractTicket> Map<String, T> getAll(Class<T> clazz);
}
