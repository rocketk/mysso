package mysso.supports.jdbc.serviceprovider.registry;

import mysso.serviceprovider.AccessServiceProviderPolicy;
import mysso.serviceprovider.ServiceProvider;
import mysso.serviceprovider.registry.ServiceProviderRegistry;
import mysso.supports.jdbc.serviceprovider.registry.mybatis.dao.ServiceProviderPoMapper;
import mysso.supports.jdbc.serviceprovider.registry.mybatis.model.ServiceProviderPo;
import mysso.supports.jdbc.serviceprovider.registry.mybatis.model.ServiceProviderPoExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * todo
 * Created by pengyu.
 */
public class JdbcServiceProviderRegistry implements ServiceProviderRegistry{
    @Autowired
    private ServiceProviderPoMapper mapper;

    @Override
    public ServiceProvider get(String spId) {
        ServiceProviderPo po = mapper.selectByPrimaryKey(spId);
        return parseFromPo(po);
    }

    @Override
    public void save(ServiceProvider sp) {
        ServiceProviderPo poFromDB = mapper.selectByPrimaryKey(sp.getId());
        if (poFromDB == null) {
            mapper.insertSelective(convertToPo(sp));
        } else {
            mapper.updateByPrimaryKey(convertToPo(sp));
        }
    }

    @Override
    public boolean delete(ServiceProvider sp) {
        return mapper.deleteByPrimaryKey(sp.getId()) > 0;
    }

    @Override
    public Map<String, ServiceProvider> getAll() {
        List<ServiceProviderPo> serviceProviderPoList = mapper.selectByExample(null);
        if (serviceProviderPoList != null) {
            Map<String, ServiceProvider> serviceProviderMap = new HashMap<>();
            for (ServiceProviderPo po : serviceProviderPoList) {
                serviceProviderMap.put(po.getId(), parseFromPo(po));
            }
            return serviceProviderMap;
        }
        return null;
    }

    private ServiceProvider parseFromPo(ServiceProviderPo po) {
        if (po == null) {
            return null;
        }
        ServiceProvider sp = new ServiceProvider();
        sp.setId(po.getId());
        sp.setName(po.getName());
        sp.setDescription(po.getDescription());
        sp.setHomeUrl(po.getHomeUrl());
        sp.setSecretKey(po.getSecretKey());
        sp.setAccessServiceProviderPolicy(AccessServiceProviderPolicy.valueOf(po.getAccessServiceProviderPolicy()));
        sp.setCreatedTime(po.getCreatedTime());
        sp.setModifiedTime(po.getModifiedTime());
        if (po.getNeededAttributes() != null) {
            Set<String> neededAttributesSet = new HashSet<>();
            String[] neededAttributesArray = po.getNeededAttributes().split(";");
            for (String attribute : neededAttributesArray) {
                neededAttributesSet.add(attribute);
            }
        }
        if (po.getLogoutUrls() != null) {
            Set<String> logoutUrlsSet = new HashSet<>();
            String[] logoutUrlsArray = po.getLogoutUrls().split(";");
            for (String logoutUrl : logoutUrlsArray) {
                logoutUrlsSet.add(logoutUrl);
            }
        }
        return sp;
    }

    private ServiceProviderPo convertToPo(ServiceProvider sp) {
        if (sp == null) {
            return null;
        }
        ServiceProviderPo po = new ServiceProviderPo();
        po.setId(sp.getId());
        po.setName(sp.getName());
        po.setSecretKey(sp.getSecretKey());
        po.setDescription(sp.getDescription());
        po.setHomeUrl(sp.getHomeUrl());
        po.setCreatedTime(sp.getCreatedTime());
        po.setModifiedTime(sp.getModifiedTime());
        po.setAccessServiceProviderPolicy(sp.getAccessServiceProviderPolicy().getIndex());
        if (sp.getLogoutUrls() != null) {
            po.setLogoutUrls(StringUtils.join(sp.getLogoutUrls(), ";"));
        }
        if (sp.getNeededAttributes() != null) {
            po.setNeededAttributes(StringUtils.join(sp.getNeededAttributes(), ";"));
        }
        return po;
    }

    public ServiceProviderPoMapper getMapper() {
        return mapper;
    }

    public void setMapper(ServiceProviderPoMapper mapper) {
        this.mapper = mapper;
    }
}
