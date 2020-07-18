package xyz.fusheng.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.fusheng.model.common.utils.Result;
import xyz.fusheng.model.core.entity.Menu;
import xyz.fusheng.model.core.entity.Role;
import xyz.fusheng.model.core.entity.RoleMenu;
import xyz.fusheng.model.core.service.MenuService;
import xyz.fusheng.model.core.service.RoleMenuService;
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
    private RoleMenuService roleMenuService;

    @Autowired
    private MenuService menuService;

    /**
     * 获取角色列表 - 查
     * @Return Result<List<Role>> 角色列表
     */
    @GetMapping("/list")
    public Result<List<Role>> list() {
        List<Role> roleList = roleService.list();
        List<Menu> menuList = new ArrayList<>(16);
        for (Role role : roleList) {
            menuList = menuService.getFormatMenuListByRoleId(role.getRoleId());
            if (menuList != null) {
                role.setMenuList(menuList);
            }
        }
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

}
