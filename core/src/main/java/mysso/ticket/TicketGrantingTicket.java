package mysso.ticket;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by pengyu on 2017/8/5.
 */
public class TicketGrantingTicket extends AbstractTicket{
    private Set<String> serviceTicketIds = new HashSet<>();
    private Set<String> tokenIds = new HashSet<>();

    public TicketGrantingTicket() {
    }

    public TicketGrantingTicket(String id, long creationTime, String credentialId) {
        super(id, creationTime, credentialId);
    }


    public Set<String> getServiceTicketIds() {
        return serviceTicketIds;
    }

    public void setServiceTicketIds(Set<String> serviceTicketIds) {
        this.serviceTicketIds = serviceTicketIds;
    }

    public Set<String> getTokenIds() {
        return tokenIds;
    }

    public void setTokenIds(Set<String> tokenIds) {
        this.tokenIds = tokenIds;
    }

    @Override
    public boolean isExpired() {
        return expired;
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
                .append(this.credentialId, that.credentialId)
                .append(this.serviceTicketIds, that.serviceTicketIds)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id)
                .append(creationTime)
                .append(credentialId)
                .append(serviceTicketIds)
                .toHashCode();
    }
}
