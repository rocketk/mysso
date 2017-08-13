package mysso.ticket;

/**
 * Created by pengyu on 2017/8/6.
 */
public class ServiceTicket extends AbstractGrantedTicket {
    public ServiceTicket(String id, long creationTime, String principalId) {
        super(id, creationTime, principalId);
    }

    public ServiceTicket(String id, long creationTime, String principalId,
                         String ticketGrantingTicketId, String serviceProviderId, long expiredTime) {
        super(id, creationTime, principalId, ticketGrantingTicketId, serviceProviderId, expiredTime);
    }
}
