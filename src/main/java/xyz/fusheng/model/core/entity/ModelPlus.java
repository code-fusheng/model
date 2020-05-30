/**
 * @FileName: ModelPlus
 * @Author: code-fusheng
 * @Date: 2020/4/29 16:05
 * Description: 增强型模版实体
 */
package xyz.fusheng.model.core.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sys_model_plus")
public class ModelPlus implements Serializable {
    private static final long serialVersionUID = -961610786115619340L;

    /**
     * 增强模版id
     */
    @TableId
    private Long modelPlusId;

    /**
     * 模版名称
     */
    private String modelPlusName;

    /**
     * 模版图片
     */
    private  String modelPlusImage;

    /**
     * 模版创建时间
     */
    private String createdTime;

    /**
     * 模版修改时间
     */
    private String updateTime;

    /**
     * 乐观锁
     */
    @Version
    private Integer version;

    /**
     * 是否启用，0否1是 默认1
     */
    private Integer isEnabled;

    /**
     * 是否删除，0否1是 默认0
     */
    private Integer isDeleted;
}
