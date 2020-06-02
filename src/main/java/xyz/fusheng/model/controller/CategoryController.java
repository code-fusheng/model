package xyz.fusheng.model.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.fusheng.model.common.enums.ResultEnums;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.common.utils.Result;
import xyz.fusheng.model.common.utils.StringUtils;
import xyz.fusheng.model.core.entity.Category;
import xyz.fusheng.model.core.entity.ModelPlus;
import xyz.fusheng.model.core.service.CategoryService;

import java.util.Arrays;
import java.util.List;

/**
 * @FileName: CategoryController
 * @Author: code-fusheng
 * @Date: 2020/5/14 12:55
 * @version: 1.0
 * Description: 分类控制器
 */

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 添加分类 - 增 - 管理员
     * @param category
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/save")
    public Result<Object> save(@RequestBody Category category){
        QueryWrapper<Category> wrapper = new QueryWrapper();
        wrapper.lambda().eq(Category::getCategoryName, category.getCategoryName());
        if( null != categoryService.getOne(wrapper) ) {
            return new Result<>("操作失败: 已存在相同分类！");
        }
        categoryService.save(category);
        return new Result<>("操作成功: 添加分类！");
    }

    /**
     * 根据id删除分类 - 删【逻辑删除】 - 管理员
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/deleteById/{id}")
    public Result<Object> deleteById(@PathVariable("id") Long id){
        categoryService.deleteById(id);
        return new Result<>("操作成功: 删除分类！");
    }

    /**
     * 修改分类 - 改 - 管理员
     * @param category
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/update")
    public Result<Object> update(@RequestBody Category category){
        QueryWrapper<Category> wrapper = new QueryWrapper();
        wrapper.lambda().eq(Category::getCategoryName, category.getCategoryName());
        if( null != categoryService.getOne(wrapper) ) {
            return new Result<>("操作失败: 已存在相同分类！");
        }
        category.setVersion(categoryService.getById(category.getCategoryId()).getVersion());
        categoryService.updateById(category);
        return new Result<>("操作成功: 修改分类!");
    }

    /**
     * 根据id查询 - 查 - 管理员
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("getById/{id}")
    public Result<Category> getById(@PathVariable("id") Long id){
        Category category = categoryService.getById(id);
        return new Result<>("操作成功: 查询分类！", category);
    }

    /**
     * 查询所有分类 - 查
     * @return
     */
    @GetMapping("/list")
    public Result<List<Category>> list() {
        List<Category> categoryList =categoryService.list();
        return new Result<>("操作成功: 查询分类列表！", categoryList);
    }

    /**
     * 多条件分页查询
     * @param page
     * @return
     */
    @PostMapping("/getByPage")
    public Result<Page<Category>> getByPage(@RequestBody Page<Category> page){
        String sortColumn = page.getSortColumn();
        String newSortColumn = StringUtils.upperCharToUnderLine(sortColumn);
        page.setSortColumn(newSortColumn);
        // 判断排序列不为空
        if(StringUtils.isNotBlank(sortColumn)){
            // 分类id、分类名称、分类文章数、分类级别、分类父级id、创建更新时间
            String[] sortColumns = { "category_id", "category_name", "article_count", "level", "pid", "created_time", "update_time"};
            List<String> sortList = Arrays.asList(sortColumns);
            if(!sortList.contains(newSortColumn.toLowerCase())) {
                return new Result<>(ResultEnums.ERROR.getCode(),"操作失败: 参数错误！");
            }
        }
        page = categoryService.getByPage(page);
        return new Result<>("操作成功: 分页查询分类！", page);
    }

    /**
     * 启用 - 改 - 管理员
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/enable/{id}")
    public Result<Object> enable(@PathVariable("id") Long id) {
        categoryService.enableById(id);
        return new Result<>("操作成功: 启用分类！");
    }

    /**
     * 弃用 - 改 - 管理员
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/disable/{id}")
    public Result<Object> disable(@PathVariable("id") Long id) {
        categoryService.disableById(id);
        return new Result<>("操作成功: 弃用分类！");
    }
}
