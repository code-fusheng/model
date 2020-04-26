/**
 * @FileName: UserController
 * @Author: code-fusheng
 * @Date: 2020/4/26 19:31
 * Description: 用户控制类
 */
package xyz.fusheng.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.fusheng.model.core.service.MenuService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private MenuService menuService;

}
