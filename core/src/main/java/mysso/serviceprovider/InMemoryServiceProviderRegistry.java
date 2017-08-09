package mysso.serviceprovider;

import javax.validation.constraints.NotNull;
import java.util.List;
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
        return null;
    }

    @Override
    public List<ServiceProvider> getAll() {
        return null;
    }

    @Override
    public boolean delete(ServiceProvider sp) {
        return false;
    }

    @Override
    public void save(ServiceProvider sp) {
    }

    public void setMap(Map<String, ServiceProvider> map) {
        this.map = map;
    }
}
