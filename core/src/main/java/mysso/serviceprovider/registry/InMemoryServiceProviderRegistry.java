package mysso.serviceprovider.registry;

import mysso.serviceprovider.ServiceProvider;
import org.apache.commons.lang3.Validate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pengyu.
 */
public class InMemoryServiceProviderRegistry implements ServiceProviderRegistry {
    private Map<String, ServiceProvider> map;

    public InMemoryServiceProviderRegistry(Map<String, ServiceProvider> map) {
        this.map = map;
    }

    @Override
    public ServiceProvider get(String spId) {
        Validate.notNull(map);
        return map.get(spId);
    }

    @Override
    public Map<String, ServiceProvider> getAll() {
        Validate.notNull(map);
        return new HashMap<>(map);
    }

    @Override
    public boolean delete(ServiceProvider sp) {
        Validate.notNull(map);
        Validate.notNull(sp);
        synchronized (this) {
            if (map.containsKey(sp.getId())) {
                map.remove(sp.getId());
                return true;
            }
        }
        return false;
    }

    @Override
    public void save(ServiceProvider sp) {
        Validate.notNull(map);
        Validate.notNull(sp);
        map.put(sp.getId(), sp);
        Validate.notNull(map);
    }

    public void setMap(Map<String, ServiceProvider> map) {
        this.map = map;
    }
}
