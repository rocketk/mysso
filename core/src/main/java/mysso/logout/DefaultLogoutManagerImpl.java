package mysso.logout;

import java.util.List;

/**
 * Created by pengyu on 2017/8/31.
 */
public class DefaultLogoutManagerImpl implements LogoutManager {
    @Override
    public LogoutResult logoutServicesByTokens(List<String> tokenIds) {
        // todo
        return null;
    }

    @Override
    public LogoutResult logoutServicesByTGT(String tgtId) {

        return new LogoutResult();
    }
}
