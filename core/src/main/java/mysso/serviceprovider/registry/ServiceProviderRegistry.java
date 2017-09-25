package mysso.serviceprovider.registry;

import mysso.serviceprovider.ServiceProvider;

import java.util.Map;

/**
 * Created by pengyu.
 */
public interface ServiceProviderRegistry {
    ServiceProvider get(String spId);
    void save(ServiceProvider sp);
    boolean delete(ServiceProvider sp);
    Map<String, ServiceProvider> getAll();
}
