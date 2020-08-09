package xyz.fusheng.model.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.fusheng.model.common.enums.StateEnums;
import xyz.fusheng.model.core.entity.Article;
import xyz.fusheng.model.core.entity.Comment;
import xyz.fusheng.model.core.mapper.ArticleMapper;
import xyz.fusheng.model.core.mapper.CommentMapper;
import xyz.fusheng.model.core.service.CommentService;

import javax.annotation.Resource;

/**
 * @FileName: CommentServiceImpl
 * @Author: code-fusheng
 * @Date: 2020/6/3 0:11
 * @version: 1.0
 * Description: 评论业务逻辑接口实现类
 */

@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private ArticleMapper articleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveComment(Comment comment) {
        // 保存评论（先执行再更新，数据真实性）
        commentMapper.insert(comment);
        // 获取评论类型
        int commentType = comment.getCommentType();
        long tid = 0;
        switch (commentType) {
            // 文章的评论，文章评论数更新
            case 0:
                tid = comment.getCommentTarget();
                // 更新文章评论数
                commentMapper.updateArticleCommentCount(tid, commentMapper.getCountByRid(tid, StateEnums.ARTICLE_COMMENT.getCode()));
                break;
            case 1:
                tid = comment.getCommentTarget();
                // 更新评论评论数
                commentMapper.updateCommentCommentCount(tid, commentMapper.getCountByTid(tid, StateEnums.COMMENT_COMMENT.getCode()));
            default:
        }

    }
}
