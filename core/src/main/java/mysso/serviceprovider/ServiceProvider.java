package mysso.serviceprovider;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pengyu on 2017/8/8.
 */
public class ServiceProvider implements Serializable {
    protected String id;
    protected String name;
    protected String description;
    protected List<String> logoutUrls;
    protected String secretKey;
    protected List<String> neededAttributes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getLogoutUrls() {
        return logoutUrls;
    }

    public void setLogoutUrls(List<String> logoutUrls) {
        this.logoutUrls = logoutUrls;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public List<String> getNeededAttributes() {
        return neededAttributes;
    }

    public void setNeededAttributes(List<String> neededAttributes) {
        this.neededAttributes = neededAttributes;
    }
}
