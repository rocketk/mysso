package mysso.ticket;

import java.io.Serializable;

/**
 * Created by pengyu on 2017/8/6.
 */
public abstract class AbstractTicket implements Serializable {
    protected String id;
    protected long creationTime;
    protected String credentialId;

    public AbstractTicket() {
    }

    public AbstractTicket(String id, long creationTime, String credentialId) {
        this.id = id;
        this.creationTime = creationTime;
        this.credentialId = credentialId;
    }

    public abstract boolean isExpired();

    public String getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(String credentialId) {
        this.credentialId = credentialId;
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
        return String.format("{}(id: {}, creationTime: {}, credentialId: {})",
                getClass().getSimpleName(), id, creationTime, credentialId);
    }
}
