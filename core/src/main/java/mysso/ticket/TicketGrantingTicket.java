package mysso.ticket;

import java.util.List;

/**
 * Created by pengyu on 2017/8/5.
 */
public class TicketGrantingTicket extends AbstractTicket{
    private List<String> serviceTicketIds;
    private List<String> tokenIds;

    public List<String> getServiceTicketIds() {
        return serviceTicketIds;
    }

    public void setServiceTicketIds(List<String> serviceTicketIds) {
        this.serviceTicketIds = serviceTicketIds;
    }

    public List<String> getTokenIds() {
        return tokenIds;
    }

    public void setTokenIds(List<String> tokenIds) {
        this.tokenIds = tokenIds;
    }

    @Override
    public boolean isExpired() {
        return false;
    }
}
