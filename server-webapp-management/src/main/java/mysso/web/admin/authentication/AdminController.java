package mysso.web.admin.authentication;

import mysso.serviceprovider.registry.ServiceProviderRegistry;
import mysso.ticket.TicketGrantingTicket;
import mysso.ticket.TicketManager;
import mysso.ticket.registry.TicketRegistry;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by pengyu.
 */
@RequestMapping("admin")
public class AdminController {
    private TicketRegistry ticketRegistry;
    private ServiceProviderRegistry serviceProviderRegistry;

    @RequestMapping("monitor")
    public String monitor(HttpServletRequest request){
        Map<String, TicketGrantingTicket> allTGTs = ticketRegistry.getAll(TicketGrantingTicket.class);
        request.setAttribute("allTGTs", allTGTs);
        return "monitor";
    }

    public void setTicketRegistry(TicketRegistry ticketRegistry) {
        this.ticketRegistry = ticketRegistry;
    }

    public void setServiceProviderRegistry(ServiceProviderRegistry serviceProviderRegistry) {
        this.serviceProviderRegistry = serviceProviderRegistry;
    }
}
