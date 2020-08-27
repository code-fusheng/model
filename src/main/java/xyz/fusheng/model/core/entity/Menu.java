/**
 * @FileName: Menu
 * @Author: code-fusheng
 * @Date: 2020/4/26 16:26
 * Description: 权限实体类’
 */
package xyz.fusheng.model.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@TableName("sys_menu")
public class Menu implements Serializable {
    private static final long serialVersionUID = 7788989908404691301L;

    /**
     * 权限ID
     */
    @TableId(value = "menu_id", type = IdType.INPUT)
    private Long menuId;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 权限级别
     */
    private Integer level;

    /**
     * 权限父级id
     */
    private Long pid;

    /**
     * 创建时间
     */
    private String createdTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 乐观锁
     */
    private Integer version;

    /**
     * 是否启用，0否1是 默认1
     */
    private Integer isEnabled;

    /**
     * 是否删除，0否1是 默认0
     */
    private Integer isDeleted;

    /**
     * 子权限
     */
    @TableField(exist = false)
    private List<Menu> childList;
}
