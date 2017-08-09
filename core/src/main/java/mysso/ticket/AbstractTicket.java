package mysso.ticket;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * Created by pengyu on 2017/8/6.
 */
public abstract class AbstractTicket implements Serializable {
    protected String id;
    protected long creationTime;
    protected String principalId;

    public abstract boolean isExpired();

    public String getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final AbstractTicket that = (AbstractTicket) o;

        return this.id != null ? this.id.equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).toHashCode();
    }

    @Override
    public String toString() {
        return String.format("{}(id: {}, creationTime: {}, principalId: {})",
                getClass().getSimpleName(), id, creationTime, principalId);
    }
}
