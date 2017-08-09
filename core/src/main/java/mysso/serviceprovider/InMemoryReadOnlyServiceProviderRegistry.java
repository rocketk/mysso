package mysso.serviceprovider;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * Created by pengyu on 2017/8/8.
 */
public class InMemoryReadOnlyServiceProviderRegistry extends AbstractReadOnlyServiceProviderRegistry {
    @NotNull
    private Map<String, ServiceProvider> map;
    @Override
    public ServiceProvider get(String spId) {
        return null;
    }

    @Override
    public List<ServiceProvider> getAll() {
        return null;
    }

    public void setMap(Map<String, ServiceProvider> map) {
        this.map = map;
    }
}
