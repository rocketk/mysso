package mysso.ticket;

import java.io.Serializable;

/**
 * Created by pengyu on 2017/8/6.
 */
public abstract class AbstractTicket implements Serializable {
    protected String id;
    protected long creationTime;
    protected String principalId;

    public AbstractTicket() {
    }

    public AbstractTicket(String id, long creationTime, String principalId) {
        this.id = id;
        this.creationTime = creationTime;
        this.principalId = principalId;
    }

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
    public String toString() {
        return String.format("{}(id: {}, creationTime: {}, principalId: {})",
                getClass().getSimpleName(), id, creationTime, principalId);
    }
}
