package mysso.serviceprovider;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by pengyu on 2017/8/8.
 */
public class ServiceProvider implements Serializable {
    protected String id;
    protected String name;
    protected String description;
    protected String secretKey;
    protected String homeUrl;
    protected List<String> logoutUrls;
    protected Map<String, String> neededAttributes;
    protected AccessServiceProviderPolicy accessServiceProviderPolicy = AccessServiceProviderPolicy.ALLOW_ALL;

    public ServiceProvider() {
    }

    public ServiceProvider(String id, String name, String description, String secretKey, List<String> logoutUrls) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.secretKey = secretKey;
        this.logoutUrls = logoutUrls;
    }


    public ServiceProvider(String id, String name, String description, String secretKey, List<String> logoutUrls,
                           Map<String, String> neededAttributes,
                           AccessServiceProviderPolicy accessServiceProviderPolicy) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.secretKey = secretKey;
        this.logoutUrls = logoutUrls;
        this.neededAttributes = neededAttributes;
        this.accessServiceProviderPolicy = accessServiceProviderPolicy;
    }

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

    public Map<String, String> getNeededAttributes() {
        return neededAttributes;
    }

    public void setNeededAttributes(Map<String, String> neededAttributes) {
        this.neededAttributes = neededAttributes;
    }

    public AccessServiceProviderPolicy getAccessServiceProviderPolicy() {
        return accessServiceProviderPolicy;
    }

    public void setAccessServiceProviderPolicy(AccessServiceProviderPolicy accessServiceProviderPolicy) {
        this.accessServiceProviderPolicy = accessServiceProviderPolicy;
    }

    public String getHomeUrl() {
        return homeUrl;
    }

    public void setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ServiceProvider that = (ServiceProvider) o;

        return new EqualsBuilder().append(this.id, that.id)
                .append(this.name, that.name)
                .append(this.description, that.description)
                .append(this.secretKey, that.secretKey)
                .append(this.logoutUrls, that.logoutUrls)
                .append(this.neededAttributes, that.neededAttributes)
                .append(this.accessServiceProviderPolicy, that.accessServiceProviderPolicy)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id)
                .append(name)
                .append(description)
                .append(secretKey)
                .append(logoutUrls)
                .append(neededAttributes)
                .append(accessServiceProviderPolicy)
                .toHashCode();
    }

}
