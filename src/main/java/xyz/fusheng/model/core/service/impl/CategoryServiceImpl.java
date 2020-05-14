/**
 * @FileName: ModelPlusServiceImpl
 * @Author: code-fusheng
 * @Date: 2020/4/29 16:35
 * Description:
 */
package xyz.fusheng.model.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.Category;
import xyz.fusheng.model.core.entity.ModelPlus;
import xyz.fusheng.model.core.mapper.CategoryMapper;
import xyz.fusheng.model.core.mapper.ModelPlusMapper;
import xyz.fusheng.model.core.service.CategoryService;
import xyz.fusheng.model.core.service.ModelPlusService;

import javax.annotation.Resource;
import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public Page<Category> getByPage(Page<Category> page) {
        // 查询数据
        List<Category> categoryList = categoryMapper.getByPage(page);
        page.setList(categoryList);
        // 统计总数
        int totalCount = categoryMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        return page;
    }
}
