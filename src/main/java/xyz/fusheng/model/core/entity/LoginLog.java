package xyz.fusheng.model.core.entity;
/**
 * @author: code-fusheng
 * @Date: 2020/9/5 20:01
 */

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @FileName: LoginLog
 * @Author: code-fusheng
 * @Date: 2020/9/5 20:01
 * @version: 1.0
 * Description: 登录日志实体类
 */

@Data
@TableName("sys_login_log")
public class LoginLog implements Serializable {

    private static final long serialVersionUID = 5382593087780757633L;

    /**
     * 登录日志编号
     */
    @TableId
    private Long loginLogId;

    /**
     * 用户名 登录账号
     */
    private String userName;

    /**
     * 登录 IP 地址
     */
    private String ipAddress;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browserType;

    /**
     * 操作系统类型
     */
    private String osType;

    /**
     * 登录状态
     * 0 成功； 1 失败
     */
    private Integer loginStatus;

    /**
     * 用户类型
     * 0 管理员；1 普通用户 默认 1
     */
    private Integer loginType;

    /**
     * 登录消息提示
     */
    private String msg;

    /**
     * 登录时间
     */
    private String loginTime;

}
