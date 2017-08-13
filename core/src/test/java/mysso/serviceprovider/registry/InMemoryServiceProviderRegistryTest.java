package mysso.serviceprovider.registry;

import mysso.serviceprovider.ServiceProvider;

import java.util.HashMap;

/**
 * Created by pengyu on 2017/8/9.
 */
public class InMemoryServiceProviderRegistryTest extends AbstractServiceProviderRegistryTest {


    @Override
    protected ServiceProviderRegistry getNewServiceProviderRegistry() {
        return new InMemoryServiceProviderRegistry(new HashMap<String, ServiceProvider>());
    }
}
