package mysso.authentication.principal;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by pengyu.
 */
public class SimpleUserAttributeRepositoryTest {
    private AttributeRepository getSimpleUserAttributeRepository() {
        Map<String, Map<String, Object>> users = new HashMap<>();
        Map<String, Object> jackAttributes = new HashMap<>();
        jackAttributes.put("department", "technology");
        jackAttributes.put("level", "manager");
        Map<String, Object> peterAttributes = new HashMap<>();
        peterAttributes.put("department", "technology");
        peterAttributes.put("level", "manager");
        users.put("jack", jackAttributes);
        users.put("peter", peterAttributes);
        SimpleUserAttributeRepository repository = new SimpleUserAttributeRepository(users);
        return repository;
    }
}