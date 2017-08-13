package mysso.ticket;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final AbstractGrantedTicket that = (AbstractGrantedTicket) o;

        return new EqualsBuilder().append(this.id, that.id)
                .append(this.creationTime, that.creationTime)
                .append(this.principalId, that.principalId)
                .append(this.ticketGrantingTicketId, that.ticketGrantingTicketId)
                .append(this.serviceProviderId, that.serviceProviderId)
                .append(this.expiredTime, that.expiredTime)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id)
                .append(creationTime)
                .append(principalId)
                .append(ticketGrantingTicketId)
                .append(serviceProviderId)
                .append(expiredTime)
                .toHashCode();
    }

}
