package xyz.fusheng.model.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.Comment;
import xyz.fusheng.model.core.vo.CommentVo;

/**
 * @FileName: CommentService
 * @Author: code-fusheng
 * @Date: 2020/6/3 0:10
 * @version: 1.0
 * Description: 评论业务逻辑接口
 */

public interface CommentService extends IService<Comment> {

    /**
     * 发表评论
     *
     * @param comment
     */
    void saveComment(Comment comment);

    /**
     * 根据id删除评论
     *
     * @param id
     */
    void deleteById(Long id);

    /**
     * 分页查询评论列表
     *
     * @param page
     * @return
     */
    Page<CommentVo> getByPage(Page<CommentVo> page);

    /**
     * 查询评论视图
     *
     * @param commentId
     */
    CommentVo getCommentVoById(Long commentId);
}
