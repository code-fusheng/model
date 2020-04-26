/**
 * @FileName: Role
 * @Author: code-fusheng
 * @Date: 2020/4/26 16:25
 * Description: 角色实体类
 */
package xyz.fusheng.model.core.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sys_role")
public class Role implements Serializable {
    private static final long serialVersionUID = -6984852563089430011L;
    /**
     * 角色ID
     */
    @TableId
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

}
