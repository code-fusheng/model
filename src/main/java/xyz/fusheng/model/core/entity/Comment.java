package xyz.fusheng.model.core.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @FileName: Comment
 * @Author: code-fusheng
 * @Date: 2020/6/2 23:25
 * @version: 1.0
 * Description: 评论实体类
 */

@Data
@TableName("m_comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 5831896222812520271L;

    /**
     * 评论id
     */
    @TableId
    private Long commentId;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 评论人id
     */
    private Long commentUserId;

    /**
     * 评论的根id,文章id
     */
    private Integer commentRoot;

    /**
     * 评论的目标id
     */
    private Long commentTarget;

    /**
     * 评论的评论量
     */
    private Integer commentCount;

    /**
     * 评论的点赞量
     */
    private Integer goodCount;

    /**
     * 评论类型
     */
    private Integer commentType;

    /**
     * @ userId
     */
    private Long commentParentUserId;

    /**
     * 评论状态 默认1 == PS： 这里理解为删除
     * 0： 正常情况
     * 1： 评论不合法 被楼主删除
     */
    private Integer commentState;

    /**
     * 评论时间
     */
    private String commentTime;

}
