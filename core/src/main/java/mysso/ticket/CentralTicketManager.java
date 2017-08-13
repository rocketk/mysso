package mysso.ticket;

import mysso.authentication.principal.Credential;
import mysso.serviceprovider.ServiceProvider;

import java.io.Serializable;

/**
 * Created by pengyu on 2017/8/5.
 */
public interface CentralTicketManager extends Serializable {
    TicketGrantingTicket createTicketGrantingTicket(Credential credential);
    ServiceTicket grantServiceTicket(String tgtId, String spId);
    Token grantToken(ServiceTicket st);

    TicketGrantingTicket getTicketGrantingTicket(String id);
    ServiceTicket getServiceTicket(String id);
    Token getToken(String id);

}
