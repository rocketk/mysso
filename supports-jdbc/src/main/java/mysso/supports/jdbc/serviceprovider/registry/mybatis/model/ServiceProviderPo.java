package mysso.supports.jdbc.serviceprovider.registry.mybatis.model;

import java.util.Date;

public class ServiceProviderPo {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column service_provider.id
     *
     * @mbg.generated Thu Sep 28 16:15:46 CST 2017
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column service_provider.name
     *
     * @mbg.generated Thu Sep 28 16:15:46 CST 2017
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column service_provider.secret_key
     *
     * @mbg.generated Thu Sep 28 16:15:46 CST 2017
     */
    private String secretKey;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column service_provider.description
     *
     * @mbg.generated Thu Sep 28 16:15:46 CST 2017
     */
    private String description;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column service_provider.home_url
     *
     * @mbg.generated Thu Sep 28 16:15:46 CST 2017
     */
    private String homeUrl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column service_provider.access_service_provider_policy
     *
     * @mbg.generated Thu Sep 28 16:15:46 CST 2017
     */
    private Integer accessServiceProviderPolicy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column service_provider.logout_urls
     *
     * @mbg.generated Thu Sep 28 16:15:46 CST 2017
     */
    private String logoutUrls;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column service_provider.needed_attributes
     *
     * @mbg.generated Thu Sep 28 16:15:46 CST 2017
     */
    private String neededAttributes;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column service_provider.created_time
     *
     * @mbg.generated Thu Sep 28 16:15:46 CST 2017
     */
    private Date createdTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column service_provider.modified_time
     *
     * @mbg.generated Thu Sep 28 16:15:46 CST 2017
     */
    private Date modifiedTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column service_provider.id
     *
     * @return the value of service_provider.id
     *
     * @mbg.generated Thu Sep 28 16:15:46 CST 2017
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column service_provider.id
     *
     * @param id the value for service_provider.id
     *
     * @mbg.generated Thu Sep 28 16:15:46 CST 2017
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column service_provider.name
     *
     * @return the value of service_provider.name
     *
     * @mbg.generated Thu Sep 28 16:15:46 CST 2017
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column service_provider.name
     *
     * @param name the value for service_provider.name
     *
     * @mbg.generated Thu Sep 28 16:15:46 CST 2017
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column service_provider.secret_key
     *
     * @return the value of service_provider.secret_key
     *
     * @mbg.generated Thu Sep 28 16:15:46 CST 2017
     */
    public String getSecretKey() {
        return secretKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column service_provider.secret_key
     *
     * @param secretKey the value for service_provider.secret_key
     *
     * @mbg.generated Thu Sep 28 16:15:46 CST 2017
     */
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey == null ? null : secretKey.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column service_provider.description
     *
     * @return the value of service_provider.description
     *
     * @mbg.generated Thu Sep 28 16:15:46 CST 2017
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column service_provider.description
     *
     * @param description the value for service_provider.description
     *
     * @mbg.generated Thu Sep 28 16:15:46 CST 2017
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column service_provider.home_url
     *
     * @return the value of service_provider.home_url
     *
     * @mbg.generated Thu Sep 28 16:15:46 CST 2017
     */
    public String getHomeUrl() {
        return homeUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column service_provider.home_url
     *
     * @param homeUrl the value for service_provider.home_url
     *
     * @mbg.generated Thu Sep 28 16:15:46 CST 2017
     */
    public void setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl == null ? null : homeUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column service_provider.access_service_provider_policy
     *
     * @return the value of service_provider.access_service_provider_policy
     *
     * @mbg.generated Thu Sep 28 16:15:46 CST 2017
     */
    public Integer getAccessServiceProviderPolicy() {
        return accessServiceProviderPolicy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column service_provider.access_service_provider_policy
     *
     * @param accessServiceProviderPolicy the value for service_provider.access_service_provider_policy
     *
     * @mbg.generated Thu Sep 28 16:15:46 CST 2017
     */
    public void setAccessServiceProviderPolicy(Integer accessServiceProviderPolicy) {
        this.accessServiceProviderPolicy = accessServiceProviderPolicy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column service_provider.logout_urls
     *
     * @return the value of service_provider.logout_urls
     *
     * @mbg.generated Thu Sep 28 16:15:46 CST 2017
     */
    public String getLogoutUrls() {
        return logoutUrls;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column service_provider.logout_urls
     *
     * @param logoutUrls the value for service_provider.logout_urls
     *
     * @mbg.generated Thu Sep 28 16:15:46 CST 2017
     */
    public void setLogoutUrls(String logoutUrls) {
        this.logoutUrls = logoutUrls == null ? null : logoutUrls.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column service_provider.needed_attributes
     *
     * @return the value of service_provider.needed_attributes
     *
     * @mbg.generated Thu Sep 28 16:15:46 CST 2017
     */
    public String getNeededAttributes() {
        return neededAttributes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column service_provider.needed_attributes
     *
     * @param neededAttributes the value for service_provider.needed_attributes
     *
     * @mbg.generated Thu Sep 28 16:15:46 CST 2017
     */
    public void setNeededAttributes(String neededAttributes) {
        this.neededAttributes = neededAttributes == null ? null : neededAttributes.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column service_provider.created_time
     *
     * @return the value of service_provider.created_time
     *
     * @mbg.generated Thu Sep 28 16:15:46 CST 2017
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column service_provider.created_time
     *
     * @param createdTime the value for service_provider.created_time
     *
     * @mbg.generated Thu Sep 28 16:15:46 CST 2017
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column service_provider.modified_time
     *
     * @return the value of service_provider.modified_time
     *
     * @mbg.generated Thu Sep 28 16:15:46 CST 2017
     */
    public Date getModifiedTime() {
        return modifiedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column service_provider.modified_time
     *
     * @param modifiedTime the value for service_provider.modified_time
     *
     * @mbg.generated Thu Sep 28 16:15:46 CST 2017
     */
    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}