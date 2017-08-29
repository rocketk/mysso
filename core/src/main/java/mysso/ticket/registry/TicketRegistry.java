package mysso.ticket.registry;

import mysso.ticket.AbstractTicket;

import java.util.Map;

/**
 * Created by pengyu on 2017/8/6.
 */
public interface TicketRegistry {
    /**
     * 添加一个 ticket
     * id 不可重复，如果 id 重复，会抛出一个 DuplicateIdException 异常
     *
     * @param ticket
     */
    void add(AbstractTicket ticket);

    /**
     * 更新 ticket
     * id 必须存在，如果 id 不存在，则会抛出一个 TicketIdNotExistsException 异常
     * @param ticket
     */
    void update(AbstractTicket ticket);

    /**
     * 销毁、删除给定的 ticket
     * 如果给定的是 TicketGrantingTicket类型，则删除其所关联的所有 service ticket 和 token
     * 如果给定的是 Service Ticket 或 Token 类型，则在他们所关联的 TicketGrantingTicket 中的引用字段中删除
     * @param id
     * @param clazz supports TicketGrantingTicket, ServiceTicket, Token
     * @return
     */
    boolean delete(String id, Class<? extends AbstractTicket> clazz);

    /**
     * 根据 id 获取指定类型的 ticket
     *
     * @param id
     * @param clazz supports TicketGrantingTicket, ServiceTicket, Token
     * @param <T>
     * @return
     */
    <T extends AbstractTicket> T get(String id, Class<T> clazz);

    /**
     * 获取指定类型全部的 ticket
     * @param clazz
     * @param <T>
     * @return
     */
    <T extends AbstractTicket> Map<String, T> getAll(Class<T> clazz);
}
