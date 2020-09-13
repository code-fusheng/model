/**
 * @FileName: RoleServiceImpl
 * @Author: code-fusheng
 * @Date: 2020/4/26 19:07
 * Description: RoleServiceImpl
 */
package xyz.fusheng.model.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.fusheng.model.core.entity.Role;
import xyz.fusheng.model.core.mapper.RoleMapper;
import xyz.fusheng.model.core.service.RoleService;

import javax.annotation.Resource;

@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public void saveRoleMenu(Long roleId, Long[] menuIds) {
        // 根据角色Id删除 sys_role_menu 全部的权限（重置）
        roleMapper.deleteRoleMenuByRoleId(roleId);
        for (Long menuId : menuIds) {
            roleMapper.saveRoleMenu(roleId, menuId);
        }

    }
}
