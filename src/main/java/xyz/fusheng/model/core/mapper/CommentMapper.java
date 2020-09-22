package xyz.fusheng.model.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.Comment;
import xyz.fusheng.model.core.vo.CommentVo;

import java.util.List;

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
     * 评论的评论数
     *
     * @param tid
     * @param types
     * @return
     */
    @Select("<script>" +
            "        select count(*) from m_comment\n" +
            "        where comment_target = #{tid} and comment_state = 0 and comment_type in\n" +
            "        <foreach collection=\"types\" separator=\",\" item=\"type\" open=\"(\" close=\")\">\n" +
            "            #{type}\n" +
            "        </foreach>" +
            "</script>")
    int getCountByTid(long tid, List<Integer> types);

    /**
     * 根据根id统计根评论数 只统计正常的评论
     * 文章的评论数
     *
     * @param rid
     * @param types
     * @return
     */
    @Select("<script>" +
            "        select count(*) from m_comment\n" +
            "        where comment_root = #{rid} and comment_state = 0 and comment_type in\n" +
            "        <foreach collection=\"types\" separator=\",\" item=\"type\" open=\"(\" close=\")\">\n" +
            "            #{type}\n" +
            "        </foreach>" +
            "</script>")
    int getCountByRid(@Param("rid") long rid, @Param("types") List<Integer> types);

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

    /**
     * 自定义多条件分页查询评论列表
     *
     * @param page
     * @return
     */
    List<CommentVo> getCommentList(Page<CommentVo> page);

    /**
     * 统计总数
     *
     * @param page
     * @return
     */
    int getCountByPage(Page<CommentVo> page);

    /**
     * 根据id获取对象内容字段
     *
     * @param commentId
     * @return
     */
    String getCommentContentById(Long commentId);

    /**
     * 查询视图对象
     *
     * @param commentId
     * @return
     */
    CommentVo getCommentVoById(Long commentId);
}
