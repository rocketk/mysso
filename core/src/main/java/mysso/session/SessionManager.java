package mysso.session;

import mysso.session.registry.SessionRegistry;
import mysso.util.UniqueIdGenerator;
import org.apache.commons.lang3.Validate;

/**
 * Created by pengyu.
 */
public class SessionManager {
    private SessionRegistry sessionRegistry;
    private UniqueIdGenerator sessionIdGeneratory;
    public Session newSession() {
        String id = sessionIdGeneratory.getNewId();
        Validate.isTrue(sessionRegistry.getSession(id) == null, "duplicated session id");
        Session session = new Session(id);
        sessionRegistry.save(session);
        return session;
    }

    public Session getSession(String id) {
        Session session = sessionRegistry.getSession(id);
        return session;
    }

    public void invalidAndRemoveSession(String id) {
        sessionRegistry.remove(id);
    }

    public void setSessionRegistry(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    public void setSessionIdGeneratory(UniqueIdGenerator sessionIdGeneratory) {
        this.sessionIdGeneratory = sessionIdGeneratory;
    }
}
