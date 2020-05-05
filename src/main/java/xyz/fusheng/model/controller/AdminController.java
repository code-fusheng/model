
package xyz.fusheng.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.fusheng.model.common.utils.Result;
import xyz.fusheng.model.common.utils.SecurityUtil;
import xyz.fusheng.model.core.entity.Menu;
import xyz.fusheng.model.core.entity.Role;
import xyz.fusheng.model.core.entity.User;
import xyz.fusheng.model.core.service.MenuService;
import xyz.fusheng.model.core.service.RoleService;
import xyz.fusheng.model.core.service.UserService;
import xyz.fusheng.model.security.entity.SelfUser;

import java.util.List;

/**
 * 管理端
 * @author code-fusheng
 */

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;

    /**
     * 管理端信息
     * @Return Result<Object> 返回数据MAP
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/info")
    public Result<Object> info(){
        SelfUser userDetails = SecurityUtil.getUserInfo();
        return new Result<>("操作成功: 管理端信息！", userDetails);
    }

    /**
     * 用户/管理员 列表
     * 拥有ADMIN或者USER角色可以访问
     * @Return Result<List<User>> 用户列表
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/list")
    public Result<List<User>> list(){
        List<User> userList = userService.list();
        return new Result<>("操作成功: 用户列表！", userList);
    }

    /**
     *
     * 拥有ADMIN角色可以访问
     * @Return Result<List<Menu>> 权限列表
     */
    @PreAuthorize("hasRole('ADMIN') and hasRole('USER')")
    @GetMapping("/menuList")
    public Result<List<Menu>> menuList(){
        List<Menu> menuList = menuService.list();
        return new Result<>("操作成功: 权限列表！", menuList);
    }


    /**
     * 拥有sys:user:info权限可以访问
     * hasPermission 第一个参数是请求路径 第二个参数是权限表达式
     * @Return Result<List<User>> 用户列表
     */
    @PreAuthorize("hasPermission('/admin/userList','user:info')")
    @GetMapping("/userList")
    public Result<List<User>> userList(){
        List<User> userList = userService.list();
        return new Result<>("操作成功: 用户列表！", userList);
    }


    /**
     * 拥有ADMIN角色和sys:role:info权限可以访问
     * @Return Result<List<Role>> 角色列表
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/adminRoleList")
    public Result<List<Role>> adminRoleList(){
        List<Role> roleList = roleService.list();
        return new Result<>("操作成功: 角色列表！", roleList);
    }
}
