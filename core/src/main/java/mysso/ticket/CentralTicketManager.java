package mysso.ticket;

import mysso.authentication.principal.Credential;

import java.io.Serializable;

/**
 * Created by pengyu on 2017/8/5.
 */
public interface CentralTicketManager extends Serializable {
    TicketGrantingTicket createTicketGrantingTicket(Credential credential);
}
