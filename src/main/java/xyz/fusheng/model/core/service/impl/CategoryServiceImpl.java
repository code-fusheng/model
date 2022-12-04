/**
 * @FileName: ModelPlusServiceImpl
 * @Author: code-fusheng
 * @Date: 2020/4/29 16:35
 * Description:
 */
package xyz.fusheng.model.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.fusheng.model.common.enums.StateEnums;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.Category;
import xyz.fusheng.model.core.mapper.CategoryMapper;
import xyz.fusheng.model.core.service.CategoryService;

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

    @Override
    public void deleteById(Long id) {
        categoryMapper.deleteById(id);
    }

    @Override
    public void enableById(Long id) {
        UpdateWrapper<Category> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(Category::getCategoryId, id);
        updateWrapper.lambda().set(Category::getIsEnabled, StateEnums.ENABLED.getCode());
        categoryMapper.update(null, updateWrapper);
    }

    @Override
    public void disableById(Long id) {
        UpdateWrapper<Category> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(Category::getCategoryId, id);
        updateWrapper.lambda().set(Category::getIsEnabled, StateEnums.NOT_ENABLE.getCode());
        categoryMapper.update(null, updateWrapper);
    }

    @Override
    public List<Category> getList() {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        // 启用状态
        queryWrapper.lambda().eq(Category::getIsEnabled, StateEnums.ENABLED.getCode());
        // 按分类文章数排序
        queryWrapper.lambda().orderByDesc(Category::getArticleCount);
        List<Category> categoryList = categoryMapper.selectList(queryWrapper);
        return categoryList;
    }
}
