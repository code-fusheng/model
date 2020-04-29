/**
 * @FileName: User
 * @Author: code-fusheng
 * @Date: 2020/4/26 16:24
 * Description: 用户实体类
 */
package xyz.fusheng.model.core.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@TableName("sys_user")
public class User implements Serializable {
    private static final long serialVersionUID = -2556069831033871039L;
    /**
     * 用户ID
     */
    @TableId
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态:NORMAL正常  PROHIBIT禁用
     */
    private String status;

    /**
     * 创建时间
     */
    private String createdTime;

    /**
     * 更新时间
     */
    private String updateTime;
}
