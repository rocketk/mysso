import mysso.serviceprovider.InMemoryServiceProviderRegistry;
import mysso.serviceprovider.ServiceProvider;
import mysso.serviceprovider.ServiceProviderRegistry;
import org.apache.commons.collections4.ListUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

/**
 * Created by pengyu on 2017/8/9.
 */
public class InMemoryServiceProviderRegistryTest {

    @Test
    public void verifyCRUD() {
        String spIdA = "sp-aaaa";
        String spIdB = "sp-bbbb";
        List<String> logoutUrlsOfA = new ArrayList<>();
        logoutUrlsOfA.add("http://www.example.com/a/logout");
        List<String> logoutUrlsOfB = new ArrayList<>();
        logoutUrlsOfA.add("http://www.example.com/b/logout");
        Map<String, ServiceProvider> map = new HashMap();
        ServiceProvider spA = new ServiceProvider(
                spIdA, "ServiceA", "this is Service A", "secretkeyA", logoutUrlsOfA);
        ServiceProvider spB = new ServiceProvider(
                spIdB, "ServiceB", "this is Service B", "secretkeyB", logoutUrlsOfB);
        map.put(spIdA, spA);
        map.put(spIdB, spB);
        ServiceProviderRegistry spr = new InMemoryServiceProviderRegistry(map);
        try {
            // verify save
            spr.save(spA);
            // verify getAll
            Assert.assertEquals(1, spr.getAll().size());
            // verify get
            Assert.assertEquals(spA, spr.get(spIdA));
            spr.save(spB);
            Assert.assertEquals(2, spr.getAll().size());
            Assert.assertEquals(spB, spr.get(spIdB));

            // verify delete
            Assert.assertTrue(spr.delete(spB));
            Assert.assertEquals(1, spr.getAll().size());
            Assert.assertNull(spr.get(spIdB));
            Assert.assertTrue(spr.delete(spA));
            Assert.assertEquals(0, spr.getAll().size());
            Assert.assertNull(spr.get(spIdA));
            // verify deleting an object that does not exist
            Assert.assertFalse(spr.delete(spA));
            Assert.assertEquals(0, spr.getAll().size());
            Assert.assertNull(spr.get(spIdA));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}
