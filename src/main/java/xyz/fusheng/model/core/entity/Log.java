/**
 * @FileName: Log
 * @Author: code-fusheng
 * @Date: 2020/4/16 10:23
 * Description: 日志实体类
 */
package xyz.fusheng.model.core.entity;

import lombok.Data;
import xyz.fusheng.model.common.excel.annotation.Excel;

import java.io.Serializable;

@Data
public class Log implements Serializable {

    private static final long serialVersionUID = 2274297318723352868L;

    /**
     * 日志id
     */
    @Excel(name = "编号")
    private Integer logId;

    /**
     * 请求路径
     */
    @Excel(name = "请求地址", autoSize = true)
    private String logUrl;

    /**
     * 参数
     */
    private String logParams;

    /**
     * 访问状态，1正常0异常
     */
    @Excel(name = "访问状态，1正常0异常")
    private Integer logStatus;

    /**
     * 异常信息
     */
    @Excel(name = "异常信息")
    private String logMessage;

    /**
     * 请求方式，get、post等
     */
    @Excel(name = "请求方式")
    private String logMethod;

    /**
     * 响应时间
     */
    @Excel(name = "响应时间（毫秒）")
    private Long logTime;

    /**
     * 返回值
     */
    private String logResult;

    /**
     * 请求ip
     */
    @Excel(name = "请求ip", autoSize = true)
    private String logIp;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间", autoSize = true)
    private String createdTime;
}
