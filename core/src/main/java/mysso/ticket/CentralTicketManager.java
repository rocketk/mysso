package mysso.ticket;

import mysso.authentication.principal.Credential;
import mysso.serviceprovider.ServiceProvider;

import java.io.Serializable;

/**
 * Created by pengyu on 2017/8/5.
 */
public interface CentralTicketManager extends Serializable {
    /**
     * create a TicketGrantingTicket and put it into the registry using the given credential.
     * @param credential
     * @return
     */
    TicketGrantingTicket createTicketGrantingTicket(Credential credential);

    /**
     * grant a ServiceTicket.
     * @param tgtId not null, existing, valid
     * @param spId not null, existing
     * @return
     */
    ServiceTicket grantServiceTicket(String tgtId, String spId);

    /**
     * grant a Token.
     * @param st not null
     * @return
     */
    Token grantToken(ServiceTicket st);

    /**
     * validate the service ticket
     * @param st
     * @return true if the service ticket is valid.
     */
    boolean validateServiceTicket(ServiceTicket st);

    /**
     * validate the token
     * @param tk
     * @return true if the token is valid
     */
    boolean validateToken(Token tk);

    /**
     * get the TicketGrantingTicket by id
     * @param id
     * @return
     */
    TicketGrantingTicket getTicketGrantingTicket(String id);

    /**
     * get the ServiceTicket by id
     * @param id
     * @return
     */
    ServiceTicket getServiceTicket(String id);

    /**
     * get Token by id
     * @param id
     * @return
     */
    Token getToken(String id);

    /**
     * destroy the TicketGrantingTicket by id, and destroy its ServiceTicket and Token
     * @param id
     */
    void destroyTicketGrantingTicket(String id);

}
