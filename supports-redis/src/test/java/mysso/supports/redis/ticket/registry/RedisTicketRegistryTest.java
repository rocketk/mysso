package mysso.supports.redis.ticket.registry;

import mysso.ticket.registry.AbstractTicketRegistryTest;
import mysso.ticket.registry.TicketRegistry;
import redis.clients.jedis.JedisPool;

/**
 * Created by pengyu.
 */
public class RedisTicketRegistryTest extends AbstractTicketRegistryTest {
    @Override
    protected TicketRegistry getNewTicketRegistry() {
        RedisTicketRegistry redisTicketRegistry = new RedisTicketRegistry();
        JedisPool pool = new JedisPool();
        redisTicketRegistry.setPool(pool);
        return redisTicketRegistry;
    }
}
