package mysso.session;

import mysso.session.registry.SessionRegistry;
import mysso.util.UniqueIdGenerator;
import org.apache.commons.lang3.Validate;

/**
 * Created by pengyu.
 */
public class SessionFactory {
    private SessionRegistry sessionRegistry;
    private UniqueIdGenerator sessionIdGeneratory;
    public Session newSession() {
        String id = sessionIdGeneratory.getNewId();
        Validate.isTrue(sessionRegistry.getSession(id) == null, "duplicated session id");
        Session session = new Session(id);
        sessionRegistry.save(session);
        return session;
    }
}
