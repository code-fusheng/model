package xyz.fusheng.model.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.fusheng.model.common.enums.ResultEnums;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.common.utils.Result;
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
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/save")
    public Result<Object> save(@RequestBody Menu menu){
        menuService.save(menu);
        return new Result<>("操作成功: 添加权限！");
    }

    /**
     * 根据id删除 物理删除 管理员
     * @param id
     * @return
     */
    @CacheEvict(cacheNames = "menu",key = "#id")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/deleteById/{id}")
    public Result<Object> deleteById(@PathVariable("id") Long id){
        menuService.removeById(id);
        return new Result<>("操作成功: 删除权限！");
    }

    /**
     * 修改
     * @param menu
     * @return
     */
    @CachePut(cacheNames = "menu",key = "#menu.menuId")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/update")
    public Result<Object> update(@RequestBody Menu menu){
        menuService.updateById(menu);
        return new Result<>("操作成功: 修改模版!");
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Cacheable(cacheNames = "menu",key = "#id")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("getById/{id}")
    public Result<Menu> getById(@PathVariable("id") Long id){
        Menu menu = menuService.getById(id);
        return new Result<>("操作成功: 查询权限！", menu);
    }

    /**
     * 查询所有权限
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/list")
    public Result<List<Menu>> list(){
        List<Menu> menues = menuService.list();
        return new Result<>("操作成功: 权限列表！", menues);
    }

    /**
     * 多条件分页查询
     * @param page
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/getByPage")
    public Result<Page<Menu>> getByPage(@RequestBody Page<Menu> page){
        // 获取排序方式  page对象中 封装了 sortColumn 排序列
        String sortColumn = page.getSortColumn();
        // 驼峰转下划线
        String newSortColumn = StringUtils.upperCharToUnderLine(sortColumn);
        page.setSortColumn(newSortColumn);
        // 判断排序列不为空
        if(StringUtils.isNotBlank(sortColumn)){
            // 权限名称，权限级别，权限父级id 创建时间， 更新时间
            String[] sortColumns = {"name", "level", "pid", "created_time", "update_time"};
            List<String> sortList = Arrays.asList(sortColumns);
            if(!sortList.contains(newSortColumn.toLowerCase())) {
                return new Result<>(ResultEnums.ERROR.getCode(),"操作失败: 参数错误！");
            }
        }
        page = menuService.getByPage(page);
        return new Result<>("操作成功: 分页查询权限！", page);
    }



}
