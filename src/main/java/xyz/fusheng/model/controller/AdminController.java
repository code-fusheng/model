
package xyz.fusheng.model.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
@Api(tags = "当前用户接口", value = "系统登录获取用户信息，角色，权限")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;

    /**
     * 获取当前用户信息 - 查
     * @Return Result<Object> 用户信息
     */
    @GetMapping("/info")
    public Result<Object> info(){
        Long userId = SecurityUtil.getUserId();
        User userInfo = new User();
        userInfo = userService.getUserInfoById(userId);
        // userInfo.setMenuList(menuService.getMenuListByRoleId(userInfo.getRoleId()));
        return new Result<>("操作成功: 当前用户信息！", userInfo);
    }

    /**
     * 查询当前用户权限列表 - 查
     * 拥有USER角色和sys:user:info权限可以访问
     * @Return Result<List<Menu>> 权限列表
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/userMenuList")
    public Result<List<Menu>> userMenuList(){
        Long userId = SecurityUtil.getUserId();
        List<Menu> menuList = userService.selectMenuByUserId(userId);
        return new Result<>("操作成功: 用户权限列表！",menuList);
    }

    /**
     * 查询当前用户角色列表 - 查
     * 拥有USER角色和sys:user:info权限可以访问
     * @Return Result<List<Role>> 角色列表
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/userRoleList")
    public Result<List<Role>> userRoleList(){
        Long userId = SecurityUtil.getUserId();
        List<Role> roleList = userService.selectRoleByUserId(userId);
        return new Result<>("操作成功: 用户角色列表！",roleList);
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
