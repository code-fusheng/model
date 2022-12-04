package xyz.fusheng.model.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.fusheng.code.springboot.core.entity.ResultVo;
import xyz.fusheng.model.common.aspect.annotation.Log;
import xyz.fusheng.model.common.aspect.enums.BusinessType;
import xyz.fusheng.model.common.enums.ResultEnums;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.common.utils.StringUtils;
import xyz.fusheng.model.core.entity.Menu;
import xyz.fusheng.model.core.service.MenuService;

import java.util.Arrays;
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
     * 添加权限 管理员
     * @param menu
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/menu/save', 'menu:list:add')")
    @PostMapping("/save")
    @Log(title = "添加权限", businessType = BusinessType.INSERT)
    public ResultVo<Object> save(@RequestBody Menu menu){
        menuService.save(menu);
        return new ResultVo<>("操作成功: 添加权限！");
    }

    /**
     * 根据id删除 物理删除 管理员
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/menu/deleteById', 'menu:list:delete')")
    @DeleteMapping("/deleteById/{id}")
    @Log(title = "删除权限", businessType = BusinessType.DELETE)
    public ResultVo<Object> deleteById(@PathVariable("id") Long id){
        menuService.deleteById(id);
        return new ResultVo<>("操作成功: 删除权限！");
    }

    /**
     * 修改
     * @param menu
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/menu/update', 'menu:list:update')")
    @PutMapping("/update")
    @Log(title = "修改权限", businessType = BusinessType.UPDATE)
    public ResultVo<Object> update(@RequestBody Menu menu){
        menuService.updateById(menu);
        return new ResultVo<>("操作成功: 修改权限!");
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/menu/getById', 'menu:list:info')")
    @GetMapping("getById/{id}")
    @Log(title = "查询权限详情", businessType = BusinessType.SELECT)
    public ResultVo<Menu> getById(@PathVariable("id") Long id){
        Menu menu = menuService.getById(id);
        return new ResultVo<>("操作成功: 查询权限！", menu);
    }

    /**
     * 查询所有权限
     *
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/menu/list', 'menu:list')")
    @GetMapping("/list")
    public ResultVo<List<Menu>> list() {
        List<Menu> menus = menuService.list();
        return new ResultVo<>("操作成功: 权限列表！", menus);
    }

    /**
     * 查询可用权限列表
     *
     * @return
     */
    @GetMapping("/getMenuTree")
    public ResultVo<List<Menu>> getMenuTree() {
        List<Menu> menuList = menuService.getMenuTree();
        return new ResultVo<>("操作成功: 可用权限列表！", menuList);
    }

    /**
     * 多条件分页查询
     *
     * @param page
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/menu/getByPage', 'menu:list')")
    @PostMapping("/getByPage")
    public ResultVo<Page<Menu>> getByPage(@RequestBody Page<Menu> page) {
        // 获取排序方式  page对象中 封装了 sortColumn 排序列
        String sortColumn = page.getSortColumn();
        // 驼峰转下划线
        String newSortColumn = StringUtils.upperCharToUnderLine(sortColumn);
        page.setSortColumn(newSortColumn);
        // 判断排序列不为空
        if(StringUtils.isNotBlank(sortColumn)){
            // 权限名称，权限级别，权限父级id 创建时间， 更新时间
            String[] sortColumns = {"name", "menu_id", "level", "pid", "created_time", "update_time"};
            List<String> sortList = Arrays.asList(sortColumns);
            if(!sortList.contains(newSortColumn.toLowerCase())) {
                return new ResultVo<>(ResultEnums.ERROR.getCode(),"操作失败: 参数错误！");
            }
        }
        page = menuService.getByPage(page);
        return new ResultVo<>("操作成功: 分页查询权限！", page);
    }



}
