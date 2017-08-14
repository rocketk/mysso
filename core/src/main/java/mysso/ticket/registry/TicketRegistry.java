package mysso.ticket.registry;

import mysso.ticket.AbstractTicket;

import java.util.Map;

/**
 * Created by pengyu on 2017/8/6.
 */
public interface TicketRegistry {
    /**
     * add the given ticket to the registry.
     * if the ticket.id already exists in the registry, then a TicketException would be thrown.
     * @param ticket
     */
    void add(AbstractTicket ticket);

    /**
     * delete the ticket by the given id from the registry
     * @param id
     * @param clazz supports TicketGrantingTicket, ServiceTicket, Token
     * @return
     */
    boolean delete(String id, Class<? extends AbstractTicket> clazz);

    /**
     * get the ticket by the given id
     * @param id
     * @param clazz supports TicketGrantingTicket, ServiceTicket, Token
     * @param <T>
     * @return
     */
    <T extends AbstractTicket> T get(String id, Class<T> clazz);

    <T extends AbstractTicket> Map<String, T> getAll(Class<T> clazz);
}
