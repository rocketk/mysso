package mysso.ticket;

/**
 * Created by pengyu.
 */
public class ServiceTicket extends AbstractGrantedTicket {
    public ServiceTicket(String id, long creationTime, String credentialId) {
        super(id, creationTime, credentialId);
    }

    public ServiceTicket(String id, long creationTime, String credentialId,
                         String ticketGrantingTicketId, String serviceProviderId, long expiredTime) {
        super(id, creationTime, credentialId, ticketGrantingTicketId, serviceProviderId, expiredTime);
    }
}
