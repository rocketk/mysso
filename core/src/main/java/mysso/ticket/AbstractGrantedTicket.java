package mysso.ticket;

/**
 * Created by pengyu on 2017/8/5.
 */
public class AbstractGrantedTicket extends AbstractTicket {
    protected String ticketGrantingTicketId;
    protected String serviceProviderId;
    protected long expiredTime;

    @Override
    public boolean isExpired() {
        return System.currentTimeMillis() >= expiredTime;
    }

    public String getTicketGrantingTicketId() {
        return ticketGrantingTicketId;
    }

    public void setTicketGrantingTicketId(String ticketGrantingTicketId) {
        this.ticketGrantingTicketId = ticketGrantingTicketId;
    }

    public String getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(String serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(long expiredTime) {
        this.expiredTime = expiredTime;
    }
}
