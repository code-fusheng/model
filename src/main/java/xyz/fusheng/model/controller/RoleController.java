package xyz.fusheng.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.fusheng.model.common.utils.Result;
import xyz.fusheng.model.core.entity.Role;
import xyz.fusheng.model.core.service.RoleService;

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

    /**
     * 获取角色列表 - 查
     * @Return Result<List<Role>> 角色列表
     */
    @GetMapping("/list")
    public Result<List<Role>> list(){
        List<Role> roleList = roleService.list();
        return new Result<>("操作成功: 角色列表！", roleList);
    }

}
