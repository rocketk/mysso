package mysso.ticket.registry;

import mysso.ticket.AbstractTicket;

/**
 * Created by pengyu on 2017/8/6.
 */
public interface TicketRegistry {
    void add(AbstractTicket ticket);

    void delete(String id, Class<? extends AbstractTicket> clazz);

    void get(String id, Class<? extends AbstractTicket> clazz);
}
