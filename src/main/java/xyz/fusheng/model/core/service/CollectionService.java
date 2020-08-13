package xyz.fusheng.model.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.Collection;
import xyz.fusheng.model.core.vo.CollectionVo;

/**
 * @FileName: CollectionService
 * @Author: code-fusheng
 * @Date: 2020/8/10 22:35
 * @version: 1.0
 * Description: 收藏业务逻辑接口
 */

public interface CollectionService extends IService<Collection> {
    /**
     * 根据用户id ，目标id，点赞类型统计收藏数
     *
     * @param userId
     * @param collectionTarget
     * @param collectionType
     * @return
     */
    int getCollectionCountByUserAndTargetAndType(long userId, Long collectionTarget, Integer collectionType);

    /**
     * 收藏
     *
     * @param collection
     */
    void doCollection(Collection collection);

    /**
     * 多条件分页查询
     *
     * @param page
     * @return
     */
    Page<CollectionVo> getByPage(Page<CollectionVo> page);
}
