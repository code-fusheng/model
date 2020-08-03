package xyz.fusheng.model.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.Category;
import xyz.fusheng.model.core.entity.ModelPlus;

import java.util.List;

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

    /**
     * 根据id逻辑删除
     * @param id
     */
    void deleteById(Long id);

    /**
     * 根据id启用
     * @param id
     */
    void enableById(Long id);

    /**
     * 根据id弃用
     *
     * @param id
     */
    void disableById(Long id);

    /**
     * 查询可用分类列表
     *
     * @return
     */
    List<Category> getList();
}
