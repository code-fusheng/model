package xyz.fusheng.model.core.entity; /**
 * @author: code-fusheng
 * @Date: 2020/9/21 16:05
 */

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @FileName: Message
 * @Author: code-fusheng
 * @Date: 2020/9/21 16:05
 * @version: 1.0
 * Description: 消息实体
 */

@Data
@TableName("m_message")
public class Message implements Serializable {

    private static final long serialVersionUID = -7354651202673728961L;

    /**
     * 消息Id
     */
    @TableId
    private Long messageId;

    /**
     * 消息目标Id
     */
    private Long messageTargetId;

    /**
     * 消息目标描述
     */
    private String messageTargetDesc;

    /**
     * 消息发送者Id
     */
    private Long sendUserId;

    /**
     * 消息接收者Id
     */
    private Long receiveUserId;

    /**
     * 消息类型
     */
    private Integer messageType;

    /**
     * 消息状态
     */
    private Integer messageState;

    /**
     * 创建时间
     */
    private String createdTime;

    /**
     * 更新时间
     */
    private String updateTime;


}
