package xyz.fusheng.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.fusheng.model.common.enums.ResultEnums;
import xyz.fusheng.model.common.utils.*;
import xyz.fusheng.model.core.entity.Comment;
import xyz.fusheng.model.core.service.CommentService;
import xyz.fusheng.model.core.service.UserService;
import xyz.fusheng.model.core.vo.CommentVo;

import java.util.Arrays;
import java.util.List;

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

    /**
     * 发表评论
     *
     * @param comment
     * @return
     */
    @PostMapping("/save")
    public Result<Object> save(@RequestBody Comment comment) {
        // 获取评论人的用户id
        comment.setCommentUserId(SecurityUtil.getUserId());
        commentService.saveComment(comment);
        return new Result<>("操作成功: 发表评论！");
    }

    /**
     * 删除评论
     *
     * @param id
     * @return
     */
    @DeleteMapping("/deleteById/{id}")
    public Result<Object> deleteById(@PathVariable("id") Long id) {
        commentService.deleteById(id);
        return new Result<>("操作成功: 删除评论！");
    }

    /**
     * 分页查询文章的评论列表 (参数带评论类型)
     *
     * @param page
     * @return
     */
    @PostMapping("/getByPage")
    public Result<Page<CommentVo>> getByPage(@RequestBody Page<CommentVo> page) {
        String sortColumn = page.getSortColumn();
        String newSortColumn = StringUtils.upperCharToUnderLine(sortColumn);
        page.setSortColumn(newSortColumn);
        if (StringUtils.isNotBlank(sortColumn)) {
            // 评论排序条件 : 评论时间、评论评论量、评论点赞量
            String[] sortColumns = {"comment_time", "comment_count", "good_count"};
            List<String> sortList = Arrays.asList(sortColumns);
            if (!sortList.contains(newSortColumn.toLowerCase())) {
                return new Result<>(ResultEnums.ERROR.getCode(), "操作失败: 参数错误！");
            }
        }
        page = commentService.getByPage(page);
        return new Result<>("操作成功: 分页查询文章评论列表！", page);
    }
}
