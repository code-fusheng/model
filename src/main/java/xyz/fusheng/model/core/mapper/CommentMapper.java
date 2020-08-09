package xyz.fusheng.model.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import xyz.fusheng.model.core.entity.Comment;

/**
 * @FileName: CommentMapper
 * @Author: code-fusheng
 * @Date: 2020/6/3 0:15
 * @version: 1.0
 * Description: 评论Mapper
 */

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 根据目标id统计目标评论数 只统计正常的评论
     *
     * @param tid
     * @param type
     * @return
     */
    @Select("select count(*) from m_comment where comment_type = #{type} and comment_target = #{tid} and comment_state = 0")
    int getCountByTid(long tid, int type);

    /**
     * 根据根id统计根评论数 只统计正常的评论
     *
     * @param rid
     * @param type
     * @return
     */
    @Select("select count(*) from m_comment where comment_type = #{type} and comment_root = #{rid} and comment_state = 0")
    int getCountByRid(long rid, int type);

    /**
     * 根据文章id和评论数，更新文章的评论数
     *
     * @param aid
     * @param commentCount
     */
    @Update("update m_article set comment_count = #{commentCount} where article_id = #{aid}")
    void updateArticleCommentCount(long aid, int commentCount);

    /**
     * 根据父级评论id和评论数，更新评论的评论数
     *
     * @param cid
     * @param commentCount
     */
    @Update("update m_comment set comment_count = #{commentCount} where comment_id = #{cid}")
    void updateCommentCommentCount(long cid, int commentCount);
}
