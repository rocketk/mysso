package mysso.serviceprovider;

import java.util.Map;

/**
 * Created by pengyu on 2017/8/8.
 */
public interface ServiceProviderRegistry {
    ServiceProvider get(String spId);
    void save(ServiceProvider sp);
    boolean delete(ServiceProvider sp);
    Map<String, ServiceProvider> getAll();
}
