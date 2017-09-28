package mysso.supports.jdbc.serviceprovider.registry;

import mysso.serviceprovider.registry.AbstractServiceProviderRegistryTest;
import mysso.serviceprovider.registry.ServiceProviderRegistry;
import mysso.supports.jdbc.serviceprovider.registry.mybatis.dao.ServiceProviderPoMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by pengyu.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring-jdbc-test.xml" })
public class JdbcServiceProviderRegistryTest extends AbstractServiceProviderRegistryTest {

    @Autowired
    private ServiceProviderRegistry registry;
    @Override
    protected ServiceProviderRegistry getNewServiceProviderRegistry() {
        return registry;
    }
}