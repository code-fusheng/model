package xyz.fusheng.model.core.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: code-fusheng
 * @Date: 2020/10/6 19:39
 */
@ApiModel(value = "xyz-fusheng-model-core-entity-OperaLog")
@Data
@TableName("sys_opera_log")
public class OperaLog {
    /**
     * 操作日志编号
     */
    @ApiModelProperty(value = "操作日志编号")
    @TableId
    private Long operaLogId;

    /**
     * 模块标题
     */
    @ApiModelProperty(value = "模块标题")
    private String operaTitle;

    /**
     * 业务类型（0其它 1新增 2修改 3删除...）
     */
    @ApiModelProperty(value = "业务类型（0其它 1新增 2修改 3删除...）")
    private Integer businessType;

    /**
     * 方法名称
     */
    @ApiModelProperty(value = "方法名称")
    private String method;

    /**
     * 请求方式
     */
    @ApiModelProperty(value = "请求方式")
    private String requestMethod;

    /**
     * 操作类型（0其它 1后台用户 2前台用户 3移动端用户）
     */
    @ApiModelProperty(value = "操作类型（0其它 1后台用户 2前台用户 3移动端用户）")
    private Integer operatorType;

    /**
     * 操作人员
     */
    @ApiModelProperty(value = "操作人员")
    private String operaName;

    /**
     * 请求URL
     */
    @ApiModelProperty(value = "请求URL")
    private String operaUrl;

    /**
     * 主机地址
     */
    @ApiModelProperty(value = "主机地址")
    private String operaIp;

    /**
     * 操作地点
     */
    @ApiModelProperty(value = "操作地点")
    private String operaLocation;

    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数")
    private String operaParam;

    /**
     * 返回参数
     */
    @ApiModelProperty(value = "返回参数")
    private String jsonResult;

    /**
     * 操作状态（0正常 1异常）
     */
    @ApiModelProperty(value = "操作状态（0正常 1异常）")
    private Integer status;

    /**
     * 错误消息
     */
    @ApiModelProperty(value = "错误消息")
    private String errorMsg;

    /**
     * 操作时间
     */
    @ApiModelProperty(value = "操作时间")
    private String operaTime;
}
