package mysso.logout;

import mysso.serviceprovider.ServiceProvider;
import mysso.ticket.Token;

/**
 * Created by pengyu on 2017/8/29.
 */
public class LogoutServiceInstance {
    private ServiceProvider serviceProvider;
    private Token token;
    private String logoutUrl;
    private String message;

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
