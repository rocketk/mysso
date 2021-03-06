package mysso.web;

import mysso.authentication.Authentication;
import mysso.serviceprovider.ServiceProvider;
import mysso.serviceprovider.registry.ServiceProviderRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by pengyu.
 */
@Controller
public class HomeController {
    private ServiceProviderRegistry serviceProviderRegistry;
    private WebUtils webUtils;
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(HttpServletRequest request, HttpServletResponse response) {
        Map<String, ServiceProvider> serviceProviderMap = serviceProviderRegistry.getAll();
        request.setAttribute("serviceProviderMap", serviceProviderMap);
        Authentication authentication = webUtils.getAuthenticationFromSession(request);

        return "home";
    }

    public void setServiceProviderRegistry(ServiceProviderRegistry serviceProviderRegistry) {
        this.serviceProviderRegistry = serviceProviderRegistry;
    }

    public void setWebUtils(WebUtils webUtils) {
        this.webUtils = webUtils;
    }
}
