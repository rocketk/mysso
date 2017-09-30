package mysso.session.registry;

import mysso.session.Session;

/**
 * Created by pengyu.
 */
public interface SessionRegistry {
    void save(Session session);
    void remove(Session session);
    void remove(String id);
    Session getSession(String id);
}
