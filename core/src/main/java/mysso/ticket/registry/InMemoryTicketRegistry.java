package mysso.ticket.registry;

import mysso.ticket.*;
import mysso.ticket.exception.DuplicateIdException;
import mysso.ticket.exception.TicketException;
import mysso.ticket.exception.UnsupportTicketTypeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

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
        Assert.notNull(ticket, "ticket should not be null");
        Map ticketMap;
        if (ticket instanceof TicketGrantingTicket) {
            ticketMap = tgtMap;
        } else if (ticket instanceof ServiceTicket) {
            ticketMap = stMap;
        } else if (ticket instanceof Token) {
            ticketMap = tkMap;
        } else {
            throw new UnsupportTicketTypeException(String.format("the ticket type is unsupported: %s", ticket.getClass()));
        }
        if (ticketMap.containsKey(ticket.getId())) {
            throw new DuplicateIdException(String.format("the id of the ticket to be added has already exists: %s", ticket.getId()));
        }
        ticketMap.put(ticket.getId(), ticket);
    }

    @Override
    public boolean delete(String id, Class<? extends AbstractTicket> clazz) {
        Assert.notNull(id, "id should not be null");
        Assert.notNull(clazz, "clazz should not be null");
        if (StringUtils.equals(TicketGrantingTicket.class.getCanonicalName(), clazz.getCanonicalName())) {
            deleteChildren(tgtMap.get(id));
            if (tgtMap.remove(id) != null) {
                return true;
            }
        } else if (StringUtils.equals(ServiceTicket.class.getCanonicalName(), clazz.getCanonicalName())) {
            if (stMap.remove(id) != null) {
                return true;
            }
        } else if (StringUtils.equals(Token.class.getCanonicalName(), clazz.getCanonicalName())) {
            if (tkMap.remove(id) != null) {
                return true;
            }
        } else {
            throw new UnsupportTicketTypeException(String.format("the ticket type is unsupported: %s", clazz));
        }
        return false;
    }

    private void deleteChildren(TicketGrantingTicket tgt) {
        if (tgt == null) {
            return;
        }
        if (tgt.getServiceTicketIds() != null) {
            for (String stId : tgt.getServiceTicketIds()) {
                stMap.remove(stId);
            }
        }
        if (tgt.getTokenIds() != null) {
            for (String tkId : tgt.getTokenIds()) {
                tkMap.remove(tkId);
            }
        }
    }

    @Override
    public <T extends AbstractTicket> T get(String id, Class<T> clazz) {
        Assert.notNull(id, "id should not be null");
        Assert.notNull(clazz, "clazz should not be null");
        Map ticketMap;
        if (StringUtils.equals(TicketGrantingTicket.class.getCanonicalName(), clazz.getCanonicalName())) {
            ticketMap = tgtMap;
        } else if (StringUtils.equals(ServiceTicket.class.getCanonicalName(), clazz.getCanonicalName())) {
            ticketMap = stMap;
        } else if (StringUtils.equals(Token.class.getCanonicalName(), clazz.getCanonicalName())) {
            ticketMap = tkMap;
        } else {
            throw new UnsupportTicketTypeException(String.format("the ticket type is unsupported: %s", clazz));
        }
        return (T) ticketMap.get(id);
    }

    @Override
    public <T extends AbstractTicket> Map<String, T> getAll(Class<T> clazz) {
        Assert.notNull(clazz, "clazz should not be null");
        Map ticketMap;
        if (StringUtils.equals(TicketGrantingTicket.class.getCanonicalName(), clazz.getCanonicalName())) {
            ticketMap = tgtMap;
        } else if (StringUtils.equals(ServiceTicket.class.getCanonicalName(), clazz.getCanonicalName())) {
            ticketMap = stMap;
        } else if (StringUtils.equals(Token.class.getCanonicalName(), clazz.getCanonicalName())) {
            ticketMap = tkMap;
        } else {
            throw new UnsupportTicketTypeException(String.format("the ticket type is unsupported: %s", clazz));
        }
        return new HashMap<>(ticketMap);
    }
}
