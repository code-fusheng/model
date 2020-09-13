/**
 * @FileName: RoleService
 * @Author: code-fusheng
 * @Date: 2020/4/26 19:06
 * Description: 角色业务逻辑接口
 */
package xyz.fusheng.model.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.fusheng.model.core.entity.Role;

public interface RoleService extends IService<Role> {

    /**
     * 保存角色和菜单之间的关系
     *
     * @param roleId
     * @param menuIds
     */
    void saveRoleMenu(Long roleId, Long[] menuIds);

}
