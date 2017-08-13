package mysso.serviceprovider.registry;

import mysso.serviceprovider.ServiceProvider;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pengyu on 2017/8/8.
 */
public class InMemoryServiceProviderRegistry implements ServiceProviderRegistry {
    @NotNull
    private Map<String, ServiceProvider> map;

    public InMemoryServiceProviderRegistry(Map<String, ServiceProvider> map) {
        this.map = map;
    }

    @Override
    public ServiceProvider get(String spId) {
        Assert.notNull(map);
        return map.get(spId);
    }

    @Override
    public Map<String, ServiceProvider> getAll() {
        Assert.notNull(map);
        return new HashMap<>(map);
    }

    @Override
    public boolean delete(ServiceProvider sp) {
        Assert.notNull(map);
        Assert.notNull(sp);
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
        Assert.notNull(map);
        Assert.notNull(sp);
        map.put(sp.getId(), sp);
        Assert.notNull(map);
    }

    public void setMap(Map<String, ServiceProvider> map) {
        this.map = map;
    }
}
