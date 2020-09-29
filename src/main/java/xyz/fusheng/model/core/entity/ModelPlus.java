/**
 * @FileName: ModelPlus
 * @Author: code-fusheng
 * @Date: 2020/4/29 16:05
 * Description: 增强型模版实体
 */
package xyz.fusheng.model.core.entity;


import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sys_model_plus")
@ApiModel(value = "modelPlus", description = "高级模版")
public class ModelPlus implements Serializable {

    private static final long serialVersionUID = -961610786115619340L;

    /**
     * 增强模版id
     */
    @TableId
    @ApiModelProperty(name = "modelPlusId", value = "高级模版编号")
    private Long modelPlusId;

    /**
     * 模版名称
     */
    @ApiModelProperty(name = "modelPlusName", value = "高级模版名称")
    private String modelPlusName;

    /**
     * 模版图片
     */
    @ApiModelProperty(name = "modelPlusImage", value = "高级模版图片")
    private  String modelPlusImage;

    /**
     * 模版创建时间
     */
    @ApiModelProperty(name = "createdTime", value = "高级模版创建时间")
    private String createdTime;

    /**
     * 模版修改时间
     */
    @ApiModelProperty(name = "updateTime", value = "高级模版修改时间")
    private String updateTime;

    /**
     * 乐观锁
     */
    @Version
    @ApiModelProperty(name = "version", value = "乐观锁")
    private Integer version;

    /**
     * 是否启用，0否1是 默认1
     */
    @ApiModelProperty(name = "isEnabled", value = "是否启用")
    private Integer isEnabled;

    /**
     * 是否删除，0否1是 默认0
     */
    @TableLogic
    @ApiModelProperty(name = "isDeleted", value = "是否删除")
    private Integer isDeleted;
}
