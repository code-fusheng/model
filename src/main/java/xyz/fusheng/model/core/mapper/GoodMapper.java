package xyz.fusheng.model.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.Good;
import xyz.fusheng.model.core.vo.GoodVo;

import java.util.List;

/**
 * @FileName: GoodMapper
 * @Author: code-fusheng
 * @Date: 2020/8/10 22:42
 * @version: 1.0
 * Description: 点赞数据持久层
 */

public interface GoodMapper extends BaseMapper<Good> {

    /**
     * 根据用户id、目标id、点赞类型查询点赞列表
     *
     * @param userId
     * @param goodTarget
     * @param goodType
     * @return
     */
    List<Good> getGoodsByUserAndTargetAndType(long userId, Long goodTarget, Integer goodType);

    /**
     * 根据用户id和目标id以及分类更新文章点赞量
     *
     * @param targetId
     * @param userId
     * @param type
     */
    void updateArticleGoodCountByTargetAndUserAndType(long targetId, long userId, Integer type);

    /**
     * 根据用户id和目标id以及分类更新评论点赞量
     *
     * @param targetId
     * @param userId
     * @param type
     */
    void updateCommentGoodCountByTargetAndUserAndType(long targetId, long userId, Integer type);

    /**
     * 多条件分页查询
     *
     * @param page
     * @return
     */
    List<GoodVo> getByPage(Page<GoodVo> page);

    /**
     * 分页查询统计总数
     *
     * @param page
     * @return
     */
    int getCountByPage(Page<GoodVo> page);

    /**
     * 查询点赞视图
     *
     * @param goodId
     * @return
     */
    GoodVo getGoodVoById(Long goodId);
}
