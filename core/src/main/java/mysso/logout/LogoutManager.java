package mysso.logout;

import java.util.Set;

/**
 * Created by pengyu.
 */
// todo
public interface LogoutManager {

    /**
     * 执行登出操作
     * @param tokenIds
     * @return
     */
    LogoutResult logoutServicesByTokens(Set<String> tokenIds);

    /**
     * 通过tgt找到其所授权的token，根据这些token的ServiceProvider找出它们的接收登出地址并发出登出请求；
     * 一个tgt可能会对应多个token，每一个token都应当执行登出操作；
     * 一个token只能对应一个ServiceProvider，但是ServiceProvider可能是集群，即多台客户端实例，
     * 必须等全部实例都成功登出了，才能销毁token，如果有一些实例未成功登出，tgt状态先置为过期
     * @param tgtId
     * @return
     */
    LogoutResult logoutServicesByTGT(String tgtId);
}
