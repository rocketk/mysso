package mysso.ticket;

/**
 * Created by pengyu on 2017/8/5.
 */
public class Token extends AbstractGrantedTicket {
    public Token(String id, long creationTime, String principalId) {
        super(id, creationTime, principalId);
    }

    public Token(String id, long creationTime, String principalId,
                         String ticketGrantingTicketId, String serviceProviderId, long expiredTime) {
        super(id, creationTime, principalId, ticketGrantingTicketId, serviceProviderId, expiredTime);
    }
}
