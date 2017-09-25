package mysso.ticket;

import java.io.Serializable;

/**
 * Created by pengyu.
 */
public abstract class AbstractTicket implements Serializable {
    protected String id;
    protected long creationTime;
    protected String credentialId;
    protected boolean expired = false;

    public AbstractTicket() {
    }

    public AbstractTicket(String id, long creationTime, String credentialId) {
        this.id = id;
        this.creationTime = creationTime;
        this.credentialId = credentialId;
    }

    public abstract boolean isExpired();

    public void markExpired(){
        this.expired = true;
    }

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
        return String.format("%s(id: %s, creationTime: %d, credentialId: %s)",
                getClass().getSimpleName(), id, creationTime, credentialId);
    }
}
