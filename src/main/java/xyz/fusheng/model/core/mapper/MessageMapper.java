package xyz.fusheng.model.core.mapper; /**
 * @author: code-fusheng
 * @Date: 2020/9/21 23:32
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.Message;
import xyz.fusheng.model.core.vo.MessageVo;

import java.util.List;

/**
 * @FileName: MessageMapper
 * @Author: code-fusheng
 * @Date: 2020/9/21 23:32
 * @version: 1.0
 * Description: 消息 Mapper
 */

@Mapper
public interface MessageMapper extends BaseMapper<Message> {

    /**
     * 多条件分页查询
     *
     * @param page
     * @return
     */
    List<MessageVo> getByPage(Page<MessageVo> page);

    /**
     * 分页查询统计总数
     *
     * @param page
     * @return
     */
    int getCountByPage(Page<MessageVo> page);

    /**
     * 根据id查询 MessageVo
     *
     * @param messageId
     * @return
     */
    MessageVo getById(Long messageId);
}
