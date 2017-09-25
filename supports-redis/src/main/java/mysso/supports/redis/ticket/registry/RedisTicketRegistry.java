package mysso.supports.redis.ticket.registry;

import mysso.ticket.*;
import mysso.ticket.exception.DuplicateIdException;
import mysso.ticket.exception.TicketIdNotExistsException;
import mysso.ticket.exception.UnsupportTicketTypeException;
import mysso.ticket.registry.TicketRegistry;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by pengyu.
 */
public class RedisTicketRegistry implements TicketRegistry {
    private Logger log = LoggerFactory.getLogger(getClass());
    private JedisPool pool;
    private String tgtPrefix = "tgt:";
    private String stPrefix = "st:";
    private String tkPrefix = "tk:";

    @Override
    public void add(AbstractTicket ticket) {
        Validate.notNull(ticket, "ticket should not be null");
        String ticketPrefix;
        if (ticket instanceof TicketGrantingTicket) {
            ticketPrefix = tgtPrefix;
        } else if (ticket instanceof ServiceTicket) {
            ticketPrefix = stPrefix;
        } else if (ticket instanceof Token) {
            ticketPrefix = tkPrefix;
        } else {
            throw new UnsupportTicketTypeException(String.format("the ticket type is unsupported: %s", ticket.getClass()));
        }
        Jedis jedis = pool.getResource();
        Boolean exists = jedis.exists(ticketPrefix + ticket.getId());
        if (exists) {
            log.warn("the id of the ticket to be added has already exists: {}, ticket type: {}",
                    ticket.getId(), ticket.getClass().getSimpleName());
            throw new DuplicateIdException(String.format("the id of the ticket to be added has already exists: " +
                    "%s, ticket type: %s", ticket.getId(), ticket.getClass().getSimpleName()));
        }
        try {
            log.trace("adding ticket to redis, type: {}, id: {}", ticket.getClass().getSimpleName(), ticket.getId());
            jedis.set(ticketPrefix + ticket.getId(), ticket2string(ticket));
            log.info("added ticket to redis, type: {}, id: {}", ticket.getClass().getSimpleName(), ticket.getId());
        } catch (Exception e) {
            log.error("an exception occurred when adding a ticket, caused by: " + e.getMessage(), e);
            throw new RedisTicketRegistryException(e.getMessage(), e);
        } finally {
            jedis.close();
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
        Jedis jedis = pool.getResource();
        Boolean exists = jedis.exists(ticketPrefix + ticket.getId());
        if (!exists) {
            log.warn("the id of the ticket to be updated does not exist, it might be deleted from the registry: " +
                    "{}, ticket type: {}", ticket.getId(), ticket.getClass().getSimpleName());
            throw new TicketIdNotExistsException(String.format("the id of the ticket to be updated does not exist, " +
                    "it might be deleted from the registry: %s, ticket type: %s", ticket.getId(), ticket.getClass().getSimpleName()));
        }
        try {
            log.trace("updating ticket to redis, type: {}, id: {}", ticket.getClass().getSimpleName(), ticket.getId());
            jedis.set(ticketPrefix + ticket.getId(), ticket2string(ticket));
            log.info("updated ticket to redis, type: {}, id: {}", ticket.getClass().getSimpleName(), ticket.getId());
        } catch (Exception e) {
            log.error("an exception occurred when updating a ticket, caused by: " + e.getMessage(), e);
            throw new RedisTicketRegistryException(e.getMessage(), e);
        } finally {
            jedis.close();
        }
    }

    @Override
    public boolean delete(String id, Class<? extends AbstractTicket> clazz) {
        Validate.notNull(id, "id should not be null");
        Validate.notNull(clazz, "clazz should not be null");
        Jedis jedis = pool.getResource();
        try {
            if (StringUtils.equals(TicketGrantingTicket.class.getCanonicalName(), clazz.getCanonicalName())) {
                deleteChildren(get(id, TicketGrantingTicket.class), jedis);
                return jedis.del(tgtPrefix + id) > 0;
            } else if (StringUtils.equals(ServiceTicket.class.getCanonicalName(), clazz.getCanonicalName())) {
                ServiceTicket st = get(id, ServiceTicket.class);
                deleteIdFromTGT(st);
                return jedis.del(stPrefix + id) > 0;
            } else if (StringUtils.equals(Token.class.getCanonicalName(), clazz.getCanonicalName())) {
                Token tk = get(id, Token.class);
                deleteIdFromTGT(tk);
                return jedis.del(tkPrefix + id) > 0;
            } else {
                throw new UnsupportTicketTypeException(String.format("the ticket type is unsupported: %s", clazz));
            }
        } catch(UnsupportTicketTypeException e){
            throw e;
        } catch (Exception e) {
            log.error("an exception occurred when deleting a ticket, caused by: " + e.getMessage(), e);
            throw new RedisTicketRegistryException(e.getMessage(), e);
        } finally {
            jedis.close();
        }
    }

    private void deleteChildren(TicketGrantingTicket tgt, Jedis jedis) {
        if (tgt == null) {
            return;
        }
        if (tgt.getServiceTicketIds() != null && tgt.getServiceTicketIds().size() > 0) {
            String[] keys = tgt.getServiceTicketIds().toArray(new String[]{});
            for (int i = 0; i < keys.length; i++) {
                keys[i] = stPrefix + keys[i];
            }
            jedis.del(keys);
        }
        if (tgt.getTokenIds() != null && tgt.getTokenIds().size() > 0) {
            String[] keys = tgt.getTokenIds().toArray(new String[]{});
            for (int i = 0; i < keys.length; i++) {
                keys[i] = tkPrefix + keys[i];
            }
            jedis.del(keys);
        }
    }

    private void deleteIdFromTGT(AbstractGrantedTicket ticket) {
        if (ticket instanceof ServiceTicket) {
            ServiceTicket st = (ServiceTicket) ticket;
            if (st != null) {
                TicketGrantingTicket tgt = get(st.getTicketGrantingTicketId(), TicketGrantingTicket.class);
                if (tgt != null) {
                    tgt.getServiceTicketIds().remove(st.getId());
                    update(tgt);
                }
            }
        } else if (ticket instanceof Token) {
            Token tk = (Token) ticket;
            if (tk != null) {
                TicketGrantingTicket tgt = get(tk.getTicketGrantingTicketId(), TicketGrantingTicket.class);
                if (tgt != null) {
                    tgt.getTokenIds().remove(tk.getId());
                    update(tgt);
                }
            }
        }
    }

    @Override
    public <T extends AbstractTicket> T get(String id, Class<T> clazz) {
        Validate.notNull(id, "id should not be null");
        Validate.notNull(clazz, "clazz should not be null");
        String ticketPrefix;
        if (StringUtils.equals(TicketGrantingTicket.class.getCanonicalName(), clazz.getCanonicalName())) {
            ticketPrefix = tgtPrefix;
        } else if (StringUtils.equals(ServiceTicket.class.getCanonicalName(), clazz.getCanonicalName())) {
            ticketPrefix = stPrefix;
        } else if (StringUtils.equals(Token.class.getCanonicalName(), clazz.getCanonicalName())) {
            ticketPrefix = tkPrefix;
        } else {
            throw new UnsupportTicketTypeException(String.format("the ticket type is unsupported: %s", clazz));
        }
        Jedis jedis = pool.getResource();
        try {
            String objStr = jedis.get(ticketPrefix + id);
            return str2ticket(objStr, clazz);
        } catch (Exception e) {
            log.error("an exception occurred when getting a ticket, caused by: " + e.getMessage(), e);
            throw new RedisTicketRegistryException(e.getMessage(), e);
        } finally {
            jedis.close();
        }
    }

    @Override
    public <T extends AbstractTicket> Map<String, T> getAll(Class<T> clazz) {
        Validate.notNull(clazz, "clazz should not be null");
        String ticketPrefix;
        if (StringUtils.equals(TicketGrantingTicket.class.getCanonicalName(), clazz.getCanonicalName())) {
            ticketPrefix = tgtPrefix;
        } else if (StringUtils.equals(ServiceTicket.class.getCanonicalName(), clazz.getCanonicalName())) {
            ticketPrefix = stPrefix;
        } else if (StringUtils.equals(Token.class.getCanonicalName(), clazz.getCanonicalName())) {
            ticketPrefix = tkPrefix;
        } else {
            throw new UnsupportTicketTypeException(String.format("the ticket type is unsupported: %s", clazz));
        }
        Jedis jedis = pool.getResource();
        try {
            Set<String> ticketKeysSet = jedis.keys(ticketPrefix + "*");
            Map<String, T> ticketMap = new HashMap<>();
            if (ticketKeysSet != null && ticketKeysSet.size() > 0) {
                String[] ticketKeysArray = ticketKeysSet.toArray(new String[]{});
                List<String> ticketStringValuesList = jedis.mget(ticketKeysArray);
                Validate.isTrue(ticketKeysSet.size() == ticketStringValuesList.size());
                for (int i = 0; i < ticketKeysArray.length; i++) {
                    ticketMap.put(ticketKeysArray[i], str2ticket(ticketStringValuesList.get(i), clazz));
                }
            }
            return ticketMap;
        } catch (Exception e) {
            log.error("an exception occurred when getting all tickets, caused by: " + e.getMessage(), e);
            throw new RedisTicketRegistryException(e.getMessage(), e);
        } finally {
            jedis.close();
        }
    }

    @Override
    public void clear() {
        Jedis jedis = pool.getResource();
        try {
            jedis.flushAll();
        } finally {
            jedis.close();
        }
    }

    private String ticket2string(AbstractTicket ticket) {
        if (ticket == null) {
            return null;
        }
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(ticket);
            byte[] bytes = baos.toByteArray();
            return Base64.encodeBase64String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RedisTicketRegistryException(e);
        } finally {
            IOUtils.closeQuietly(oos);
            IOUtils.closeQuietly(baos);
        }
    }

    private <T extends AbstractTicket> T str2ticket(String objStr, Class<T> clazz) {
        if (objStr == null) {
            return null;
        }
        byte[] objBytes = Base64.decodeBase64(objStr);
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            bais = new ByteArrayInputStream(objBytes);
            ois = new ObjectInputStream(bais);
            Object obj = ois.readObject();
            if (obj != null) {
                Validate.isInstanceOf(clazz, obj, "the ticket object should be an instant of %s", clazz.getName());
                return (T) obj;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RedisTicketRegistryException(e);
        } finally {
            IOUtils.closeQuietly(ois);
            IOUtils.closeQuietly(bais);
        }
    }

    public JedisPool getPool() {
        return pool;
    }

    public void setPool(JedisPool pool) {
        this.pool = pool;
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
