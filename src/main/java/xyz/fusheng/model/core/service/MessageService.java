package xyz.fusheng.model.core.service; /**
 * @author: code-fusheng
 * @Date: 2020/9/21 23:42
 */

import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.Message;
import xyz.fusheng.model.core.vo.MessageVo;

import java.util.List;

/**
 * @FileName: MessageService
 * @Author: code-fusheng
 * @Date: 2020/9/21 23:42
 * @version: 1.0
 * Description: 消息业务逻辑接口
 */

public interface MessageService {


    /**
     * 添加消息
     *
     * @param message
     */
    void save(Message message);

    /**
     * 删除消息
     *
     * @param messageIds
     * @return
     */
    void deleteByIds(List<Long> messageIds);

    /**
     * 修改消息
     *
     * @param message
     */
    void update(Message message);

    /**
     * 确认消息
     *
     * @param messageIds
     */
    void confirmMessageByIds(List<Long> messageIds);

    /**
     * 查询消息
     *
     * @param messageId
     * @return
     */
    MessageVo getById(Long messageId);

    /**
     * 多条件分页查询
     *
     * @param page
     * @return
     */
    Page<MessageVo> getByPage(Page<MessageVo> page);

    /**
     * 查询当前用户消息列表
     *
     * @return
     */
    List<Message> getList();

    /**
     * 查询所有消息
     *
     * @return
     */
    List<Message> getAll();
}
