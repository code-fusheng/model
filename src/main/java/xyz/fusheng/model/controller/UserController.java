package xyz.fusheng.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.fusheng.model.common.utils.Result;
import xyz.fusheng.model.core.entity.Menu;
import xyz.fusheng.model.core.service.MenuService;
import xyz.fusheng.model.security.entity.SelfUser;

import java.util.List;

/**
 * 普通用户
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private MenuService menuService;

    /**
     * 用户端信息
     * @Return Result<Object> 用户端信息
     */
    @GetMapping("/info")
    public Result<Object> userLogin(){
        SelfUser userDetails = (SelfUser) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        return new Result<>("操作成功: 用户端信息！", userDetails);
    }

    /**
     * 拥有USER角色和sys:user:info权限可以访问
     * @Return Result<List<Menu>> 权限列表
     */
    @PreAuthorize("hasRole('USER') and hasPermission('/user/menuList','sys:user:info')")
    @GetMapping("/menuList")
    public Result<List<Menu>> menu(){
        List<Menu> menuList = menuService.list();
        return new Result<>("操作成功: 权限列表！",menuList);
    }

}
