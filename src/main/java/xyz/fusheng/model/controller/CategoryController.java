package xyz.fusheng.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.fusheng.model.common.enums.ResultEnums;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.common.utils.Result;
import xyz.fusheng.model.common.utils.StringUtils;
import xyz.fusheng.model.core.entity.Category;
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
     * 查询所有分类 - 查
     * @return
     */
    @GetMapping("/getList")
    public Result<List<Category>> getList() {
        List<Category> categoryList =categoryService.list();
        return new Result<>("操作成功: 查询所有分类！", categoryList);
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
}
