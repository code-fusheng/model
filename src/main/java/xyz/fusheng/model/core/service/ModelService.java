/**
 * @FileName: ModelService
 * @Author: code-fusheng
 * @Date: 2020/4/14 23:19
 * Description: 模版业务逻辑接口
 */
package xyz.fusheng.model.core.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.Model;

import java.util.List;

public interface ModelService {

    /**
     * 保存
     * @param model
     */
    void save(Model model);

    /**
     * 根据id删除
     * @param id
     */
    @CacheEvict(cacheNames = "model",key = "#id")
    void deleteById(Integer id);

    /**
     * 批量删除
     * @param ids
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 修改
     * @param model
     */
    @CachePut(cacheNames = "model",key = "#model.modelId")
    void update(Model model);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Cacheable(cacheNames = "model",key = "#id")
    Model getById(Integer id);

    /**
     * 查询所有
     * @return
     */
    List<Model> getAll();

    /**
     * 分页查询
     * @param page
     * @return
     */
    Page<Model> getByPage(Page<Model> page);

    /**
     * 根据id启用
     * @param id
     */
    void enableById(Integer id);

    /**
     * 根据id弃用
     * @param id
     */
    void disableById(Integer id);

}
