package mysso.serviceprovider.registry;

import mysso.serviceprovider.AccessServiceProviderPolicy;
import mysso.serviceprovider.ServiceProvider;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created by pengyu.
 */
public abstract class AbstractServiceProviderRegistryTest {
    @Test
    public void verifyCRUD() {
        String spIdA = "sp-aaaa";
        String spIdB = "sp-bbbb";
        Set<String> logoutUrlsOfA = new HashSet<>();
        logoutUrlsOfA.add("http://www.example.com/a/logout");
        Set<String> logoutUrlsOfB = new HashSet<>();
        logoutUrlsOfB.add("http://www.example.com/b/logout");
        Map<String, ServiceProvider> map = new HashMap();
        ServiceProvider spA = new ServiceProvider(
                spIdA, "ServiceA", "this is Service A", "secretkeyA", logoutUrlsOfA);
        ServiceProvider spB = new ServiceProvider(
                spIdB, "ServiceB", "this is Service B", "secretkeyB", logoutUrlsOfB);
        ServiceProviderRegistry spr = getNewServiceProviderRegistry();
        Assert.assertNotNull(spr);
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
    }

    @Test
    public void verifyUpdateExistingServiceProvider() {
        String spId = "sp-aaaa";
        Set<String> logoutUrlsOfA = new HashSet<>();
        logoutUrlsOfA.add("http://www.example.com/a/logout");
        Map<String, ServiceProvider> map = new HashMap();
        ServiceProvider sp = new ServiceProvider(
                spId, "ServiceA", "this is Service A", "secretkeyA", logoutUrlsOfA);
        ServiceProviderRegistry spr = getNewServiceProviderRegistry();
        Assert.assertNotNull(spr);
        spr.save(sp);
        Assert.assertEquals(1, spr.getAll().size());
        Assert.assertEquals(sp, spr.get(spId));
        ServiceProvider sp2 = new ServiceProvider(
                spId, "ServiceA_updated", "updated service a", "updated secretkey", logoutUrlsOfA);
        spr.save(sp2);
        Assert.assertEquals(1, spr.getAll().size());
        Assert.assertEquals(sp2, spr.get(spId));
    }

    @Test
    public void verifyAddFullFieldsNotNull() {
        ServiceProvider sp = createServiceProviderWithFullFields("test-id");
        ServiceProviderRegistry registry = getNewServiceProviderRegistry();
        registry.save(sp);
        ServiceProvider spFromDB = registry.get(sp.getId());
        Assert.assertEquals(sp, spFromDB);
        Assert.assertNotNull(spFromDB.getName());
        Assert.assertNotNull(spFromDB.getDescription());
        Assert.assertNotNull(spFromDB.getAccessServiceProviderPolicy());
        Assert.assertNotNull(spFromDB.getHomeUrl());
        Assert.assertNotNull(spFromDB.getLogoutUrls());
        Assert.assertNotNull(spFromDB.getNeededAttributes());
        Assert.assertNotNull(spFromDB.getSecretKey());
        Assert.assertNotNull(spFromDB.getCreatedTime());
        Assert.assertNotNull(spFromDB.getModifiedTime());
        Assert.assertEquals(sp.getLogoutUrls().size(), spFromDB.getLogoutUrls().size());
        Assert.assertEquals(sp.getNeededAttributes().size(), spFromDB.getNeededAttributes().size());
    }

    protected ServiceProvider createServiceProviderWithFullFields(String id) {
        ServiceProvider sp = new ServiceProvider();
        sp.setId(id);
        sp.setName(id + ":name");
        sp.setDescription("this is test sp");
        sp.setHomeUrl("http://homeurl");
        sp.setSecretKey("secret");
        sp.setAccessServiceProviderPolicy(AccessServiceProviderPolicy.ALLOW_ALL);
        Set<String> logoutUrls = new HashSet<>();
        logoutUrls.add("http://www.example.com/" + id + "/logout");
        sp.setLogoutUrls(logoutUrls);
        Set<String> neededAttributes = new HashSet<>();
        neededAttributes.add("attr");
        sp.setNeededAttributes(neededAttributes);
        Date now = new Date();
        sp.setCreatedTime(now);
        sp.setModifiedTime(now);
        return sp;
    }

    protected abstract ServiceProviderRegistry getNewServiceProviderRegistry();
}
