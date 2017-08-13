package mysso.ticket;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

/**
 * Created by pengyu on 2017/8/5.
 */
public class TicketGrantingTicket extends AbstractTicket{
    private List<String> serviceTicketIds;
    private List<String> tokenIds;

    public TicketGrantingTicket() {
    }

    public TicketGrantingTicket(String id, long creationTime, String principalId) {
        super(id, creationTime, principalId);
    }

    public List<String> getServiceTicketIds() {
        return serviceTicketIds;
    }

    public void setServiceTicketIds(List<String> serviceTicketIds) {
        this.serviceTicketIds = serviceTicketIds;
    }

    public List<String> getTokenIds() {
        return tokenIds;
    }

    public void setTokenIds(List<String> tokenIds) {
        this.tokenIds = tokenIds;
    }

    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final TicketGrantingTicket that = (TicketGrantingTicket) o;

        return new EqualsBuilder().append(this.id, that.id)
                .append(this.creationTime, that.creationTime)
                .append(this.principalId, that.principalId)
                .append(this.serviceTicketIds, that.serviceTicketIds)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id)
                .append(creationTime)
                .append(principalId)
                .append(serviceTicketIds)
                .toHashCode();
    }
}
