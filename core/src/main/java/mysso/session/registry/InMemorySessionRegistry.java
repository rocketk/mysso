package mysso.session.registry;

import mysso.session.Session;
import org.apache.commons.lang3.Validate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pengyu.
 */
public class InMemorySessionRegistry implements SessionRegistry {
    private Map<String, Session> sessions = new HashMap<>();
    @Override
    public void save(Session session) {
        Validate.notNull(session, "session is null");
        Validate.notNull(session.getId(), "session.id is null");
        sessions.put(session.getId(), session);
    }

    @Override
    public void remove(Session session) {
        Validate.notNull(session, "session is null");
        Validate.notNull(session.getId(), "session.id is null");
        sessions.remove(session.getId());
    }

    @Override
    public void remove(String id) {
        Validate.notNull(id, "session id is null");
        sessions.remove(id);
    }

    @Override
    public Session getSession(String id) {
        Validate.notNull(id, "session id is null");
        return sessions.get(id);
    }
}
