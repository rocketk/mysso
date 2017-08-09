import mysso.serviceprovider.InMemoryServiceProviderRegistry;
import mysso.serviceprovider.ServiceProvider;
import mysso.serviceprovider.ServiceProviderRegistry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pengyu on 2017/8/9.
 */
public class InMemoryServiceProviderRegistryTest {

    @Test
    public void verifyCRUDWithoutCollectionFields() {
        String spIdA = "sp-aaaa";
        String spIdB = "sp-bbbb";
        Map<String, ServiceProvider> map = new HashMap();
        map.put(spIdA, new ServiceProvider(
                spIdA, "ServiceA", "this is Service A", "secretkeyA", null));
        map.put(spIdB, new ServiceProvider(
                spIdB, "ServiceB", "this is Service B", "secretkeyB", null));
        ServiceProviderRegistry spr = new InMemoryServiceProviderRegistry(map);
        
    }
}
