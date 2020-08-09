package xyz.fusheng.model.core.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Id;

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
    private Long commentUser;

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
     * 0:直接评论   ？（评论） @ ？（文章）
     * 1:评论的评论 带有指定的父级评论对象 存在回复的用户 ？（评论） @ ？（评论）
     */
    private Integer commentType;

    /**
     * 评论状态 默认1 == PS： 这里理解为删除
     * 0： 正常情况
     * 1： 评论不合法 被楼主删除
     */
    private Integer commentStats;

    /**
     * 评论时间
     */
    private String commentTime;

}
