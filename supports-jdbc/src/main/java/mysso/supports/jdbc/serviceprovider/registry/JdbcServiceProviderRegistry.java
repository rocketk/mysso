package mysso.supports.jdbc.serviceprovider.registry;

import mysso.serviceprovider.ServiceProvider;
import mysso.serviceprovider.registry.ServiceProviderRegistry;

import java.util.Map;

/**
 * Created by pengyu.
 */
public class JdbcServiceProviderRegistry implements ServiceProviderRegistry{
    @Override
    public ServiceProvider get(String spId) {
        return null;
    }

    @Override
    public void save(ServiceProvider sp) {

    }

    @Override
    public boolean delete(ServiceProvider sp) {
        return false;
    }

    @Override
    public Map<String, ServiceProvider> getAll() {
        return null;
    }
}
