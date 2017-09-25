package mysso.authentication.exception;

/**
 * Created by pengyu.
 */
public class CredentialNotSupportedException extends AuthenticationException {
    public CredentialNotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CredentialNotSupportedException() {
    }

    public CredentialNotSupportedException(String message) {
        super(message);
    }

    public CredentialNotSupportedException(Throwable cause) {
        super(cause);
    }

    public CredentialNotSupportedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
