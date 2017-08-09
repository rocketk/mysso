package mysso.serviceprovider;

/**
 * Created by pengyu on 2017/8/8.
 */
public class ServiceProviderException extends RuntimeException {
    public ServiceProviderException() {
    }

    public ServiceProviderException(String message) {
        super(message);
    }

    public ServiceProviderException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceProviderException(Throwable cause) {
        super(cause);
    }

    public ServiceProviderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
