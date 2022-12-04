package xyz.fusheng.model.controller; /**
 * @author: code-fusheng
 * @Date: 2020/9/21 23:49
 */

import org.springframework.web.bind.annotation.*;
import xyz.fusheng.code.springboot.core.entity.ResultVo;
import xyz.fusheng.model.common.enums.ResultEnums;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.common.utils.SecurityUtil;
import xyz.fusheng.model.common.utils.StringUtils;
import xyz.fusheng.model.core.entity.Message;
import xyz.fusheng.model.core.service.MessageService;
import xyz.fusheng.model.core.vo.MessageVo;

import javax.annotation.Resource;
import java.util.Arrays;
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
     * @param message
     * @return
     */
    @PostMapping("/save")
    public ResultVo<Object> save(@RequestBody Message message) {
        message.setSendUserId(SecurityUtil.getUserId());
        messageService.save(message);
        return new ResultVo<>("操作成功: 添加消息！");
    }

    /**
     * 批量/删除消息 - 删 - 管理员/用户
     *
     * @param messageIds
     * @return
     */
    @DeleteMapping("/deleteByIds")
    public ResultVo<Object> deleteByIds(@RequestBody List<Long> messageIds) {
        messageService.deleteByIds(messageIds);
        return new ResultVo<>("操作成功: 删除消息！");
    }

    /**
     * 修改消息 - 改 - 管理员
     *
     * @param message
     * @return
     */
    @PutMapping("/update")
    public ResultVo<Object> update(@RequestBody Message message) {
        messageService.update(message);
        return new ResultVo<>("操作成功: 更新消息！");
    }

    /**
     * 批量/确认消息 - 改 - 用户
     *
     * @param messageIds
     * @return
     */
    @PutMapping("confirmMessageByIds")
    public ResultVo<Object> confirmMessageByIds(@RequestBody List<Long> messageIds) {
        messageService.confirmMessageByIds(messageIds);
        return new ResultVo<>("操作成功: 确认消息！");
    }

    /**
     * 查询消息 - 查 - 所有人
     *
     * @param messageId
     * @return
     */
    @GetMapping("/getById/{messageId}")
    public ResultVo<MessageVo> getById(@PathVariable("messageId") Long messageId) {
        MessageVo messageVo = messageService.getById(messageId);
        return new ResultVo<>("操作成功: 查询消息！", messageVo);
    }

    /**
     * 查询当前用户消息列表
     *
     * @return
     */
    @GetMapping("/getList")
    public ResultVo<List<Message>> getList() {
        List<Message> messageList = messageService.getList();
        return new ResultVo<>(messageList);
    }

    /**
     * 查询所有
     *
     * @return
     */
    @GetMapping("/getAll")
    public ResultVo<List<Message>> getAll() {
        List<Message> messageList = messageService.getAll();
        return new ResultVo<>(messageList);
    }

    /**
     * 分页查询消息 - 查
     *
     * @param page
     * @return
     */
    @PostMapping("/getByPage")
    public ResultVo<Page<MessageVo>> getByPage(@RequestBody Page<MessageVo> page) {
        String sortColumn = page.getSortColumn();
        String newSortColumn = StringUtils.upperCharToUnderLine(sortColumn);
        page.setSortColumn(newSortColumn);
        if (StringUtils.isNotBlank(sortColumn)) {
            // 发送者ID、接收者ID、消息目标ID、创建修改时间
            String[] sortColumns = {"send_user_id", "receive_user_id", "message_target_id", "created_time", "update_time"};
            List<String> sortList = Arrays.asList(sortColumns);
            if (!sortList.contains(newSortColumn.toLowerCase())) {
                return new ResultVo<>(ResultEnums.ERROR.getCode(), "操作失败: 参数错误！");
            }
        }
        page = messageService.getByPage(page);
        return new ResultVo<>("操作成功: 分页查询消息！", page);
    }


}
