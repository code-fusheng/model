package xyz.fusheng.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.fusheng.model.common.aspect.annotation.Log;
import xyz.fusheng.model.common.aspect.enums.BusinessType;
import xyz.fusheng.model.common.utils.Result;
import xyz.fusheng.model.core.entity.Menu;
import xyz.fusheng.model.core.entity.Role;
import xyz.fusheng.model.core.service.MenuService;
import xyz.fusheng.model.core.service.RoleService;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: RoleController
 * @Author: code-fusheng
 * @Date: 2020/5/11 16:13
 * @version: 1.0
 * Description: 角色控制类
 */

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    /**
     * 添加角色 - 增 - 管理员
     *
     * @param role
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/role/save', 'role:list:add')")
    @PostMapping("/save")
    @Log(title = "添加角色", businessType = BusinessType.INSERT)
    public Result<Object> save(@RequestBody Role role) {
        roleService.save(role);
        return new Result<>("操作成功: 添加角色！");
    }

    /**
     * 删除角色 - 删 - 管理员
     *
     * @param roleId
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/role/deleteById', 'role:list:delete')")
    @DeleteMapping("/deleteById/{roleId}")
    @Log(title = "删除角色", businessType = BusinessType.DELETE)
    public Result<Object> deleteById(@PathVariable("roleId") Long roleId) {
        // 删除角色的同时需要删除用户角色中间表中的数据
        roleService.deleteById(roleId);
        return new Result<>("操作成功: 删除角色！");
    }

    /**
     * 修改角色 - 该 - 管理员
     *
     * @param role
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/role/update', 'role:list:update')")
    @PutMapping("/update")
    @Log(title = "修改角色", businessType = BusinessType.UPDATE)
    public Result<Object> update(@RequestBody Role role) {
        role.setVersion(roleService.getById(role.getRoleId()).getVersion());
        roleService.updateById(role);
        return new Result<>("操作成功: 修改角色！");
    }

    /**
     * 根据id查询角色 - 查 - 管理员
     *
     * @param roleId
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/role/getById', 'role:list:info')")
    @GetMapping("/getById/{roleId}")
    @Log(title = "查询角色详情", businessType = BusinessType.SELECT)
    public Result<Role> getById(@PathVariable("roleId") Long roleId) {
        Role role = roleService.getById(roleId);
        return new Result<>("操作成功: 查询角色！", role);
    }

    /**
     * 获取角色列表 - 查
     *
     * @Return Result<List < Role>> 角色列表
     */
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/role/list', 'role:list')")
    @GetMapping("/list")
    public Result<List<Role>> list() {
        List<Role> roleList = roleService.list();
        return new Result<>("操作成功: 角色列表！", roleList);
    }

    /**
     * 查询所有可用角色
     *
     * @return
     */
    @GetMapping("/selectAllRole")
    public Result<List<Role>> selectAllRole() {
        List<Role> roleList = roleService.selectAllRole();
        return new Result<>("操作成功: 查询可用角色！", roleList);
    }

    /**
     * 根据用户ID查询用户拥有的角色IDS - 查
     *
     * @param userId
     * @return
     */
    @GetMapping("/getRoleIdsByUserId/{userId}")
    public Result<List<Long>> getRoleIdsByUserId(@PathVariable Long userId) {
        List<Long> roleIds = roleService.getRoleIdsByUserId(userId);
        return new Result<>("操作成功: 获取用户角色Ids！", roleIds);
    }

    /**
     * 获取角色权限列表 - 查
     *
     * @param id
     * @return
     */
    @GetMapping("/getMenuListByRoleId/{id}")
    public Result<List<Menu>> getMenuListByRoleId(@PathVariable Long id) {
        List<Menu> menuList = menuService.getMenuListByRoleId(id);
        return new Result<>("操作成功: 角色权限列表！", menuList);
    }

    /**
     * 获取角色权限列表(格式化) - 查
     *
     * @param id
     * @return
     */
    @GetMapping("/getFormatMenuListByRoleId/{id}")
    public Result<List<Menu>> getFormatMenuListByRoleId(@PathVariable Long id) {
        List<Menu> menuList = menuService.getFormatMenuListByRoleId(id);
        return new Result<>("操作成功: 角色权限列表！", menuList);
    }

    /**
     * 根据角色id获取菜单权限ids
     *
     * @param roleId
     * @return
     */
    @GetMapping("/getMenuIdsByRoleId/{roleId}")
    public Result<List<Long>> getMenuIdsByRoleId(@PathVariable Long roleId) {
        List<Long> ids = menuService.getMenuIdsByRoleId(roleId);
        return new Result<>("操作成功: 角色对应的权限IDs！", ids);
    }

    /**
     * 保存更新角色绑定权限
     *
     * @param roleId
     * @param menuIds
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/role/saveRoleMenu', 'role:list:menu')")
    @PostMapping("/saveRoleMenu/{roleId}/{menuIds}")
    @Log(title = "更新角色权限", businessType = BusinessType.GRANT)
    public Result<Object> saveRoleMenu(@PathVariable Long roleId, @PathVariable Long[] menuIds) {
        /**
         * 因为我们用的路径参数，前端可能传过来的menuIds是一个空的，但是为空的话无法匹配上面的路径
         * 所以如果为空，我们让前端传一个-1过来，如果是-1说明当前角色一个权限也没有选择
         */
        if (menuIds.length == 1 && menuIds[0].equals(-1L)) {
            menuIds = new Long[]{};
        }
        roleService.saveRoleMenu(roleId, menuIds);
        return new Result<>("操作成功: 更新角色权限！");
    }

    /**
     * 保存更新用户角色
     * @param userId
     * @param roleIds
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/role/saveUserRole', 'user:list:role')")
    @PostMapping("/saveUserRole/{userId}/{roleIds}")
    @Log(title = "更新用户角色", businessType = BusinessType.GRANT)
    public Result<Object> saveUserRole(@PathVariable Long userId, @PathVariable Long[] roleIds) {
        /**
         * 因为我们用的路径参数，前端可能传过来的roleIds是一个空的，但是为空的话无法匹配上面的路径
         * 所以如果为空，我们让前端传一个-1过来，如果是-1说明当前用户一个角色也没有选择
         */
        if (roleIds.length == 1 && roleIds[0].equals(-1L)) {
            roleIds = new Long[]{};
        }
        roleService.saveUserRole(userId, roleIds);
        return new Result<>("操作成功: 更新用户角色");
    }

    /**
     * 启用 - 改 - 管理员
     *
     * @param roleId
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/role/enable', 'role:list:enable')")
    @PutMapping("/enable/{roleId}")
    @Log(title = "启用角色", businessType = BusinessType.ENABLE)
    public Result<Object> enable(@PathVariable("roleId") Long roleId) {
        roleService.enableById(roleId);
        return new Result<>("操作成功: 启用角色！");
    }

    /**
     * 弃用 - 改 - 管理员
     *
     * @param roleId
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/role/disable', 'role:list:disable')")
    @PutMapping("/disable/{roleId}")
    @Log(title = "弃用角色", businessType = BusinessType.DISABLE)
    public Result<Object> disable(@PathVariable("roleId") Long roleId) {
        roleService.disableById(roleId);
        return new Result<>("操作成功: 弃用角色！");
    }

}
