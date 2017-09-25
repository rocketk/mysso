package mysso.logout;

import mysso.protocol1.Constants;
import mysso.protocol1.dto.LogoutResultDto;
import mysso.serviceprovider.ServiceProvider;
import mysso.serviceprovider.registry.ServiceProviderRegistry;
import mysso.ticket.TicketGrantingTicket;
import mysso.ticket.Token;
import mysso.ticket.registry.TicketRegistry;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by pengyu.
 */
public class DefaultLogoutManagerImpl implements LogoutManager {
    private Logger log = LoggerFactory.getLogger(getClass());
    private LogoutHandler logoutHandler;
    private TicketRegistry ticketRegistry;
    private ServiceProviderRegistry serviceProviderRegistry;
    @Override
    public LogoutResult logoutServicesByTokens(Set<String> tokenIds) {
        LogoutResult logoutResult = new LogoutResult();
        logoutResult.setAllSuccess(true);
        if (tokenIds == null || tokenIds.size() == 0) {
            return logoutResult;
        }
        final ArrayList<LogoutServiceInstance> successList = new ArrayList<>();
        final ArrayList<LogoutServiceInstance> failedList = new ArrayList<>();
        logoutResult.setSuccessLogoutServiceInstance(successList);
        logoutResult.setFailLogoutServiceInstance(failedList);
        for (String tokenId : tokenIds) {
            Token token = ticketRegistry.get(tokenId, Token.class);
            if (token == null) {
                log.warn("token not exists: " + tokenId);
                LogoutServiceInstance logoutServiceInstance = new LogoutServiceInstance();
                logoutServiceInstance.setMessage("token not exists: " + tokenId);
                failedList.add(logoutServiceInstance);
                logoutResult.setAllSuccess(false);
                continue;
            }
            final ServiceProvider serviceProvider = serviceProviderRegistry.get(token.getServiceProviderId());
            if (serviceProvider == null) {
                log.warn("serviceProvider not exists: " + token.getServiceProviderId());
                LogoutServiceInstance logoutServiceInstance = new LogoutServiceInstance();
                logoutServiceInstance.setMessage("serviceProvider not exists: " + token.getServiceProviderId());
                failedList.add(logoutServiceInstance);
                logoutResult.setAllSuccess(false);
                continue;
            }
            log.info("perform logout request to service client, logout urls: {}, tokenId: {}, serviceProviderId: {}",
                    StringUtils.join(serviceProvider.getLogoutUrls()), tokenId, serviceProvider.getId());
            if (serviceProvider.getLogoutUrls() != null) {
                for (String url : serviceProvider.getLogoutUrls()) {
                    LogoutServiceInstance logoutServiceInstance = new LogoutServiceInstance();
                    final LogoutResultDto logoutResultDto;
                    logoutServiceInstance.setLogoutUrl(url);
                    logoutServiceInstance.setServiceProvider(serviceProvider);
                    logoutServiceInstance.setToken(token);
                    try {
                        logoutResultDto = logoutHandler.logoutViaBackchannel(url, tokenId);
                        logoutServiceInstance.setMessage(logoutResultDto.getMessage());
                        switch (logoutResultDto.getCode()) {
                            case Constants.SLO_CODE_SUCCESS:
                            case Constants.SLO_CODE_TOKEN_NONEXISTS:
                                successList.add(logoutServiceInstance);
                                break;
                            default:
                                failedList.add(logoutServiceInstance);
                                break;
                        }
                    } catch (IOException e) {
                        logoutServiceInstance.setMessage(e.getMessage());
                        failedList.add(logoutServiceInstance);
                    }
                }
            } else {
                log.warn("the service provider {} has no logoutUrls, spid: {}",
                        serviceProvider.getName(), serviceProvider.getId());
            }
        }
        return logoutResult;
    }

    @Override
    public LogoutResult logoutServicesByTGT(String tgtId) {
        final TicketGrantingTicket tgt = ticketRegistry.get(tgtId, TicketGrantingTicket.class);
        final Set<String> tokenIds = tgt.getTokenIds();
        return logoutServicesByTokens(tokenIds);
    }

    public void setLogoutHandler(LogoutHandler logoutHandler) {
        this.logoutHandler = logoutHandler;
    }

    public void setTicketRegistry(TicketRegistry ticketRegistry) {
        this.ticketRegistry = ticketRegistry;
    }

    public void setServiceProviderRegistry(ServiceProviderRegistry serviceProviderRegistry) {
        this.serviceProviderRegistry = serviceProviderRegistry;
    }
}
