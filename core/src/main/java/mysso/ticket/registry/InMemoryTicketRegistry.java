package mysso.ticket.registry;

import mysso.ticket.*;
import mysso.ticket.exception.DuplicateIdException;
import mysso.ticket.exception.TicketIdNotExistsException;
import mysso.ticket.exception.UnsupportTicketTypeException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pengyu.
 */
public class InMemoryTicketRegistry implements TicketRegistry {
    private Map<String, TicketGrantingTicket> tgtMap = new HashMap<>();
    private Map<String, ServiceTicket> stMap = new HashMap<>();
    private Map<String, Token> tkMap = new HashMap<>();

    @Override
    public void add(AbstractTicket ticket) {
        Validate.notNull(ticket, "ticket should not be null");
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
            throw new DuplicateIdException(String.format("the id of the ticket to be added has already exists: %s, ticket type: %s", ticket.getId(), ticket.getClass().getSimpleName()));
        }
        ticketMap.put(ticket.getId(), ticket);
    }

    @Override
    public void update(AbstractTicket ticket) {
        Validate.notNull(ticket, "ticket should not be null");
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
        if (!ticketMap.containsKey(ticket.getId())) {
            throw new TicketIdNotExistsException(String.format("the id of the ticket to be updated does not exist, " +
                    "it might be deleted from the registry: %s, ticket type: %s", ticket.getId(), ticket.getClass().getSimpleName()));
        }
        ticketMap.put(ticket.getId(), ticket);
    }

    @Override
    public boolean delete(String id, Class<? extends AbstractTicket> clazz) {
        Validate.notNull(id, "id should not be null");
        Validate.notNull(clazz, "clazz should not be null");
        if (StringUtils.equals(TicketGrantingTicket.class.getCanonicalName(), clazz.getCanonicalName())) {
            deleteChildren(tgtMap.get(id));
            if (tgtMap.remove(id) != null) {
                return true;
            }
        } else if (StringUtils.equals(ServiceTicket.class.getCanonicalName(), clazz.getCanonicalName())) {
            ServiceTicket st = get(id, ServiceTicket.class);
            deleteIdFromTGT(st);
            if (stMap.remove(id) != null) {
                return true;
            }
        } else if (StringUtils.equals(Token.class.getCanonicalName(), clazz.getCanonicalName())) {
            Token tk = get(id, Token.class);
            deleteIdFromTGT(tk);
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

    private void deleteIdFromTGT(AbstractGrantedTicket ticket) {
        if (ticket instanceof ServiceTicket) {
            ServiceTicket st = (ServiceTicket) ticket;
            if (st != null) {
                TicketGrantingTicket tgt = get(st.getTicketGrantingTicketId(), TicketGrantingTicket.class);
                if (tgt != null) {
                    tgt.getServiceTicketIds().remove(st.getId());
                    tgtMap.put(tgt.getId(), tgt);
                }
            }
        } else if (ticket instanceof Token) {
            Token tk = (Token) ticket;
            if (tk != null) {
                TicketGrantingTicket tgt = get(tk.getTicketGrantingTicketId(), TicketGrantingTicket.class);
                if (tgt != null) {
                    tgt.getTokenIds().remove(tk.getId());
                    tgtMap.put(tgt.getId(), tgt);
                }
            }
        }
    }

    @Override
    public <T extends AbstractTicket> T get(String id, Class<T> clazz) {
        Validate.notNull(id, "id should not be null");
        Validate.notNull(clazz, "clazz should not be null");
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
        Validate.notNull(clazz, "clazz should not be null");
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

    @Override
    public void clear() {
        tgtMap.clear();
        stMap.clear();
        tkMap.clear();
    }
}
