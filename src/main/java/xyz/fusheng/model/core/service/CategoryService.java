package xyz.fusheng.model.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.Category;
import xyz.fusheng.model.core.entity.ModelPlus;

/**
 * @FileName: CategoryService
 * @Author: code-fusheng
 * @Date: 2020/5/14 12:56
 * @version: 1.0
 * Description: 分类业务逻辑
 */

public interface CategoryService extends IService<Category> {
    /**
     * 多条件分页查询
     * @param page
     * @return
     */
    Page<Category> getByPage(Page<Category> page);
}
