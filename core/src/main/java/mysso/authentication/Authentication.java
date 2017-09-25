package mysso.authentication;

import mysso.authentication.principal.Principal;
import mysso.ticket.TicketGrantingTicket;

import java.util.Date;
import java.util.Map;

/**
 * Created by pengyu.
 */
public class Authentication {
    private Principal principal;
    private Date authenticationDate;
    private TicketGrantingTicket ticketGrantingTicket;
    private Map<String, Object> attributes;
    private boolean success;
    private String message;

    public Authentication() {
    }

    public Authentication(Principal principal, Date authenticationDate,
                          TicketGrantingTicket ticketGrantingTicket,
                          Map<String, Object> attributes, boolean success,
                          String message) {
        this.principal = principal;
        this.authenticationDate = authenticationDate;
        this.ticketGrantingTicket = ticketGrantingTicket;
        this.attributes = attributes;
        this.success = success;
        this.message = message;
    }

    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    public Date getAuthenticationDate() {
        return authenticationDate;
    }

    public void setAuthenticationDate(Date authenticationDate) {
        this.authenticationDate = authenticationDate;
    }

    public TicketGrantingTicket getTicketGrantingTicket() {
        return ticketGrantingTicket;
    }

    public void setTicketGrantingTicket(TicketGrantingTicket ticketGrantingTicket) {
        this.ticketGrantingTicket = ticketGrantingTicket;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
