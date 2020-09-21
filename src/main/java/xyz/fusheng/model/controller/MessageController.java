package xyz.fusheng.model.controller; /**
 * @author: code-fusheng
 * @Date: 2020/9/21 23:49
 */

import org.springframework.web.bind.annotation.*;
import xyz.fusheng.model.common.utils.Result;
import xyz.fusheng.model.common.utils.SecurityUtil;
import xyz.fusheng.model.core.entity.Message;
import xyz.fusheng.model.core.service.MessageService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @FileName: MessageController
 * @Author: code-fusheng
 * @Date: 2020/9/21 23:49
 * @version: 1.0
 * Description: 消息控制类
 */

@RestController
@RequestMapping("/message")
public class MessageController {

    @Resource
    private MessageService messageService;

    /**
     * 添加消息 - 增 - 所有人
     *
     * @param message
     * @return
     */
    @PostMapping("/save")
    public Result<Object> save(@RequestBody Message message) {
        message.setSendUserId(SecurityUtil.getUserId());
        messageService.save(message);
        return new Result<>("操作成功: 添加消息！");
    }

    /**
     * 删除消息 - 删 - 管理员/用户
     *
     * @param messageIds
     * @return
     */
    @DeleteMapping("/deleteByIds")
    public Result<Object> deleteByIds(@RequestBody Long[] messageIds) {
        messageService.deleteByIds(messageIds);
        return new Result<>("操作成功: 删除消息！");
    }

    /**
     * 修改消息 - 改 - 管理员
     *
     * @param message
     * @return
     */
    @PutMapping("/update")
    public Result<Object> update(@RequestBody Message message) {
        messageService.update(message);
        return new Result<>("操作成功: 更新消息！");
    }

    @PutMapping("confirmMessageIds")
    public Result<Object> confirmMessageByIds(@RequestBody Long[] messageIds) {
        messageService.confirmMessageByIds(messageIds);
        return new Result<>("操作成功: 确认消息！");
    }


}
