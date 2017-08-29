package mysso.supports.redis.ticket.registry;

import mysso.ticket.*;
import mysso.ticket.exception.DuplicateIdException;
import mysso.ticket.exception.TicketIdNotExistsException;
import mysso.ticket.exception.UnsupportTicketTypeException;
import mysso.ticket.registry.TicketRegistry;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pengyu on 17-8-29.
 */
public class RedisTicketRegistry implements TicketRegistry {
    private Logger log = LoggerFactory.getLogger(getClass());
    private Jedis jedis;
    private String tgtPrefix = "tgt:";
    private String stPrefix = "st:";
    private String tkPrefix = "tk:";

    @Override
    public void add(AbstractTicket ticket) {
        Validate.notNull(ticket, "ticket should not be null");
        String ticketPrefix = null;
        if (ticket instanceof TicketGrantingTicket) {
            ticketPrefix = tgtPrefix;
        } else if (ticket instanceof ServiceTicket) {
            ticketPrefix = stPrefix;
        } else if (ticket instanceof Token) {
            ticketPrefix = tkPrefix;
        } else {
            throw new UnsupportTicketTypeException(String.format("the ticket type is unsupported: %s", ticket.getClass()));
        }
        Boolean exists = jedis.exists(ticketPrefix + ticket.getId());
        if (exists) {
            log.warn("the id of the ticket to be added has already exists: {}, ticket type: {}",
                    ticket.getId(), ticket.getClass().getSimpleName());
            throw new DuplicateIdException(String.format("the id of the ticket to be added has already exists: " +
                    "%s, ticket type: %s", ticket.getId(), ticket.getClass().getSimpleName()));
        }
        try {
            final Map<String, String> describe = BeanUtils.describe(ticket);
            log.trace("adding ticket to redis, type: {}, id: {}", ticket.getClass().getSimpleName(), ticket.getId());
            jedis.hmset(ticketPrefix + ticket.getId(), describe);
            log.info("added ticket to redis, type: {}, id: {}", ticket.getClass().getSimpleName(), ticket.getId());
        } catch (Exception e) {
            log.error("an exception occurred when adding a ticket, caused by: " + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    @Override
    public void update(AbstractTicket ticket) {
        Validate.notNull(ticket, "ticket should not be null");
        String ticketPrefix = null;
        if (ticket instanceof TicketGrantingTicket) {
            ticketPrefix = tgtPrefix;
        } else if (ticket instanceof ServiceTicket) {
            ticketPrefix = stPrefix;
        } else if (ticket instanceof Token) {
            ticketPrefix = tkPrefix;
        } else {
            throw new UnsupportTicketTypeException(String.format("the ticket type is unsupported: %s", ticket.getClass()));
        }
        Boolean exists = jedis.exists(ticketPrefix + ticket.getId());
        if (!exists) {
            log.warn("the id of the ticket to be updated does not exist, it might be deleted from the registry: " +
                    "{}, ticket type: {}", ticket.getId(), ticket.getClass().getSimpleName());
            throw new TicketIdNotExistsException(String.format("the id of the ticket to be updated does not exist, " +
                    "it might be deleted from the registry: %s, ticket type: %s", ticket.getId(), ticket.getClass().getSimpleName()));
        }
        try {
            log.trace("updating ticket to redis, type: {}, id: {}", ticket.getClass().getSimpleName(), ticket.getId());
            final Map<String, String> describe = BeanUtils.describe(ticket);
            jedis.hmset(ticketPrefix + ticket.getId(), describe);
            log.info("updated ticket to redis, type: {}, id: {}", ticket.getClass().getSimpleName(), ticket.getId());
        } catch (Exception e) {
            log.error("an exception occurred when adding a ticket, caused by: " + e.getMessage(), e);
            e.printStackTrace();
        }
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

    public Jedis getJedis() {
        return jedis;
    }

    public void setJedis(Jedis jedis) {
        this.jedis = jedis;
    }

    public String getTgtPrefix() {
        return tgtPrefix;
    }

    public void setTgtPrefix(String tgtPrefix) {
        this.tgtPrefix = tgtPrefix;
    }

    public String getStPrefix() {
        return stPrefix;
    }

    public void setStPrefix(String stPrefix) {
        this.stPrefix = stPrefix;
    }

    public String getTkPrefix() {
        return tkPrefix;
    }

    public void setTkPrefix(String tkPrefix) {
        this.tkPrefix = tkPrefix;
    }
}
