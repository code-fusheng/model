package xyz.fusheng.model.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.Collection;
import xyz.fusheng.model.core.vo.CollectionVo;

import java.util.List;

/**
 * @FileName: CollectionMapper
 * @Author: code-fusheng
 * @Date: 2020/8/10 22:41
 * @version: 1.0
 * Description: 收藏数据持久层
 */

public interface CollectionMapper extends BaseMapper<Collection> {

    /**
     * 根据用户id、目标id、点赞类型查询收藏列表
     *
     * @param userId
     * @param collectionTarget
     * @param collectionType
     * @return
     */
    List<Collection> getCollectionsByUserAndTargetAndType(long userId, Long collectionTarget, Integer collectionType);

    /**
     * 根据用户id和目标id以及分类更新文章收藏量
     *
     * @param targetId
     * @param userId
     * @param type
     */
    void updateArticleCollectionCountByTargetAndUserAndType(long targetId, long userId, Integer type);

    /**
     * 多条件分页查询
     *
     * @param page
     * @return
     */
    List<CollectionVo> getByPage(Page<CollectionVo> page);

    /**
     * 分页查询统计总数
     *
     * @param page
     * @return
     */
    int getCountByPage(Page<CollectionVo> page);
}
