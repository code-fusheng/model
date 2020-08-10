package xyz.fusheng.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.fusheng.model.common.utils.Result;
import xyz.fusheng.model.common.utils.SearchUtils;
import xyz.fusheng.model.common.utils.SecurityUtil;
import xyz.fusheng.model.core.entity.Comment;
import xyz.fusheng.model.core.service.CommentService;
import xyz.fusheng.model.core.service.UserService;

/**
 * @FileName: CommentController
 * @Author: code-fusheng
 * @Date: 2020/6/3 0:09
 * @version: 1.0
 * Description: 评论控制类
 */

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/save")
    public Result<Comment> save(@RequestBody Comment comment) {
        // 获取评论人的用户id
        comment.setCommentUserId(SecurityUtil.getUserId());
        commentService.saveComment(comment);
        return new Result<>("操作成功: 发表评论！");
    }


}
