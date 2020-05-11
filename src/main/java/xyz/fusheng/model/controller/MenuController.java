package xyz.fusheng.model.controller;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.fusheng.model.common.utils.Result;
import xyz.fusheng.model.core.entity.Menu;
import xyz.fusheng.model.core.entity.User;
import xyz.fusheng.model.core.service.MenuService;

import java.util.List;

/**
 * @FileName: MenuController
 * @Author: code-fusheng
 * @Date: 2020/5/11 16:03
 * @version: 1.0
 * Description: 权限控制类
 */

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 获取权限列表 - 查
     * @Return Result<List<Menu>> 权限列表
     */
    @GetMapping("/list")
    public Result<List<Menu>> list(){
        List<Menu> menuList = menuService.list();
        return new Result<>("操作成功: 权限列表！", menuList);
    }



}
