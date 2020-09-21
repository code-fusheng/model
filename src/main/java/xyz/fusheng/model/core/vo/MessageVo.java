package xyz.fusheng.model.core.vo; /**
 * @author: code-fusheng
 * @Date: 2020/9/21 23:13
 */

import lombok.Data;
import xyz.fusheng.model.core.entity.Message;

/**
 * @FileName: MessageVo
 * @Author: code-fusheng
 * @Date: 2020/9/21 23:13
 * @version: 1.0
 * Description: 消息视图对象
 */

@Data
public class MessageVo extends Message {

    /**
     * 发送者用户名
     */
    private String sendUserName;

    /**
     * 接收者用户名
     */
    private String receiveUserName;

}

