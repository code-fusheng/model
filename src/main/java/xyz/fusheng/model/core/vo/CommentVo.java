package xyz.fusheng.model.core.vo;

import lombok.Data;
import xyz.fusheng.model.core.entity.Comment;

/**
 * @FileName: CommentVo
 * @Author: code-fusheng
 * @Date: 2020/6/2 23:41
 * @version: 1.0
 * Description: 视图层评论实体
 */

@Data
public class CommentVo extends Comment {

    /**
     * 评论人用户名
     */
    private String username;

    /**
     * @ username
     */
    private String commentParentUserName;

    /**
     * 评论人头像
     */
    private String header;

    /**
     * 评论点赞标识 仅用于前端展示，不做数据持久化
     */
    private boolean goodCommentFlag = false;

}
