/**
 * @FileName: Menu
 * @Author: code-fusheng
 * @Date: 2020/4/26 16:26
 * Description: 权限实体类’
 */
package xyz.fusheng.model.core.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sys_menu")
public class Menu implements Serializable {
    private static final long serialVersionUID = 7788989908404691301L;

    /**
     * 权限ID
     */
    @TableId
    private Long menuId;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限标识
     */
    private String permission;

}
