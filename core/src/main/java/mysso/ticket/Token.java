package mysso.ticket;

/**
 * Created by pengyu on 2017/8/5.
 */
public class Token extends AbstractGrantedTicket {
    public Token(String id, long creationTime, String credentialId) {
        super(id, creationTime, credentialId);
    }

    public Token(String id, long creationTime, String credentialId,
                         String ticketGrantingTicketId, String serviceProviderId, long expiredTime) {
        super(id, creationTime, credentialId, ticketGrantingTicketId, serviceProviderId, expiredTime);
    }

}
