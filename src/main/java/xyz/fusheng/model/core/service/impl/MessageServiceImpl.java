package xyz.fusheng.model.core.service.impl; /**
 * @author: code-fusheng
 * @Date: 2020/9/21 23:44
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.stereotype.Service;
import xyz.fusheng.model.common.enums.StateEnums;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.common.utils.SecurityUtil;
import xyz.fusheng.model.core.entity.Message;
import xyz.fusheng.model.core.mapper.MessageMapper;
import xyz.fusheng.model.core.service.MessageService;
import xyz.fusheng.model.core.vo.MessageVo;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @FileName: MessageServiceImpl
 * @Author: code-fusheng
 * @Date: 2020/9/21 23:44
 * @version: 1.0
 * Description: 消息业务逻辑接口实现类
 */

@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private MessageMapper messageMapper;

    @Override
    public void save(Message message) {
        messageMapper.insert(message);
    }

    @Override
    public void deleteByIds(List<Long> messageIds) {
        if (messageIds != null && messageIds.size() > 0) {
            messageMapper.deleteBatchIds(messageIds);
        }
    }

    @Override
    public void update(Message message) {
        messageMapper.updateById(message);
    }

    @Override
    public void confirmMessageByIds(List<Long> messageIds) {
        if (messageIds != null && messageIds.size() > 0) {
            for (Long id : messageIds) {
                UpdateWrapper<Message> updateWrapper = new UpdateWrapper();
                updateWrapper.lambda().eq(Message::getMessageId, id);
                updateWrapper.lambda().set(Message::getMessageState, StateEnums.MESSAGE_IS_READ.getCode());
                messageMapper.update(null, updateWrapper);
            }
        }
    }

    @Override
    public MessageVo getById(Long messageId) {
        return messageMapper.getById(messageId);
    }

    @Override
    public Page<MessageVo> getByPage(Page<MessageVo> page) {
        // 查询数据
        List<MessageVo> messageVoList = messageMapper.getByPage(page);
        page.setList(messageVoList);
        // 统计总数
        int totalCount = messageMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        return page;
    }

    @Override
    public List<Message> getList() {
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Message::getReceiveUserId, SecurityUtil.getUserId());
        return messageMapper.selectList(queryWrapper);
    }

    @Override
    public List<Message> getAll() {
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        return messageMapper.selectList(queryWrapper);
    }
}
