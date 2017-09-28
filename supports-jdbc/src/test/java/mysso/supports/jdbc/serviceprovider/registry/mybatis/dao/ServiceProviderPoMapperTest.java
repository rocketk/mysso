package mysso.supports.jdbc.serviceprovider.registry.mybatis.dao;

import mysso.supports.jdbc.serviceprovider.registry.mybatis.model.ServiceProviderPo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
/**
 * Created by pengyu.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring-jdbc-test.xml" })
public class ServiceProviderPoMapperTest {
    @Autowired
    private ServiceProviderPoMapper mapper;
    @Test
    public void testCrud() {
        ServiceProviderPo po = new ServiceProviderPo();
        po.setId("testid");
        po.setAccessServiceProviderPolicy(1);
        po.setDescription("test sp");
        po.setHomeUrl("http://sp/home");
        po.setLogoutUrls("http://sp/logout");
        po.setName("testsp");
        po.setSecretKey("testsp");
        Date now = new Date();
        po.setCreatedTime(now);
        po.setModifiedTime(now);
        Assert.assertEquals(1, mapper.insert(po));
        ServiceProviderPo poFromDB = mapper.selectByPrimaryKey(po.getId());
        Assert.assertNotNull(mapper.selectByPrimaryKey(po.getId()));
        Assert.assertEquals(po.getId(), poFromDB.getId());
        Assert.assertEquals(po.getName(), poFromDB.getName());
        Assert.assertEquals(1, mapper.deleteByPrimaryKey(po.getId()));
        Assert.assertNull(mapper.selectByPrimaryKey(po.getId()));
    }
}
