package mysso.supports.redis.ticket.registry;

/**
 * Created by pengyu on 2017/9/24.
 */
public class RedisTicketRegistryException extends RuntimeException {
    public RedisTicketRegistryException() {
    }

    public RedisTicketRegistryException(String message) {
        super(message);
    }

    public RedisTicketRegistryException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedisTicketRegistryException(Throwable cause) {
        super(cause);
    }

    public RedisTicketRegistryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
