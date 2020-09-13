package xyz.fusheng.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
     * 获取角色列表 - 查
     * @Return Result<List<Role>> 角色列表
     */
    @GetMapping("/list")
    public Result<List<Role>> list() {
        List<Role> roleList = roleService.list();
        // List<Menu> menuList = new ArrayList<>(16);
        // for (Role role : roleList) {
        //     menuList = menuService.getFormatMenuListByRoleId(role.getRoleId());
        //     if (menuList.size() > 0) {
        //         role.setMenuList(menuList);
        //     }
        // }
        return new Result<>("操作成功: 角色列表！", roleList);
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
    @PostMapping("/saveRoleMenu/{roleId}/{menuIds}")
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


}
