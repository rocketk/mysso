package mysso.logout;

import mysso.protocol1.dto.LogoutResultDto;

import java.io.IOException;

/**
 * Created by pengyu on 2017/8/28.
 */
public interface LogoutHandler {
    LogoutResultDto logoutViaBackchannel(String url, String token) throws IOException;
}
