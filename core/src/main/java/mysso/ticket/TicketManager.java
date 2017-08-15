package mysso.ticket;

import mysso.authentication.principal.Credential;
import mysso.serviceprovider.ServiceProvider;

import java.io.Serializable;

/**
 * Created by pengyu on 2017/8/5.
 */
public interface TicketManager extends Serializable {
    /**
     * create a TicketGrantingTicket and put it into the registry using the given credential.
     * @param credential
     * @return
     */
    TicketGrantingTicket createTicketGrantingTicket(Credential credential);

    /**
     * grant a ServiceTicket.
     * @param tgt not null, existing, valid
     * @param spId not null, existing
     * @return
     */
    ServiceTicket grantServiceTicket(TicketGrantingTicket tgt, String spId);

    /**
     * grant a Token.
     * @param st not null
     * @return
     */
    Token grantToken(ServiceTicket st);

    /**
     * 校验一个ServiceTicketID是否正确, 并且是否由给定的ServiceProvider所签发
     * @param stId
     * @param spId
     * @return
     */
    boolean validateServiceTicket(String stId, String spId);

    /**
     * 校验一个TokenID是否正确, 并且是否由给定的ServiceProvider所签发
     * @param tkId
     * @param spId
     * @return
     */
    boolean validateToken(String tkId, String spId);

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
