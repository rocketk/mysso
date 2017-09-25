package mysso.logout;

import mysso.protocol1.dto.LogoutResultDto;

import java.io.IOException;

/**
 * Created by pengyu.
 */
public interface LogoutHandler {
    LogoutResultDto logoutViaBackchannel(String url, String token) throws IOException;
}
