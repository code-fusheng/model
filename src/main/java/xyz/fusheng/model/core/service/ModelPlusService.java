/**
 * @FileName: ModelPlusService
 * @Author: code-fusheng
 * @Date: 2020/4/29 16:32
 * Description:
 */
package xyz.fusheng.model.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.ModelPlus;

/**
 * @author code-fusheng
 *
 */

public interface ModelPlusService extends IService<ModelPlus> {
    /**
     * 多条件分页查询
     * @param page
     * @return
     */
    Page<ModelPlus> getByPage(Page<ModelPlus> page);

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
     * @param id
     */
    void disableById(Long id);
}
