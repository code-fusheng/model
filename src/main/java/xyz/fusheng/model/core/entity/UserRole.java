/**
 * @FileName: UserRole
 * @Author: code-fusheng
 * @Date: 2020/4/26 18:56
 * Description: 用户角色关系实体
 */
package xyz.fusheng.model.core.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sys_user_menu")
public class UserRole implements Serializable {
    private static final long serialVersionUID = 2667274665940198129L;
    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 角色ID
     */
    private Long roleId;
}
