package mysso.serviceprovider;

/**
 * Created by pengyu on 2017/8/8.
 */
public abstract class AbstractReadOnlyServiceProviderRegistry implements ServiceProviderRegistry {
    @Override
    public boolean delete(ServiceProvider sp) {
        throw new ServiceProviderException(String.format("method 'delete' is not supported by the implementation {}", getClass().getSimpleName()));
    }

    @Override
    public void save(ServiceProvider sp) {
        throw new ServiceProviderException(String.format("method 'save' is not supported by the implementation {}", getClass().getSimpleName()));
    }
}
