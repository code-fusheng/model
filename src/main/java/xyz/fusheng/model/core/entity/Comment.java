package xyz.fusheng.model.core.entity;

import com.baomidou.mybatisplus.annotation.TableId;
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
     * 评论的文章id
     */
    private Long commentArticle;

    /**
     * 评论的评论量 一级评论才显示
     */
    private Integer commentCount;

    /**
     * 评论的点赞量
     */
    private Integer goodCount;

    /**
     * 评论的类型
     * 1:直接评论   ？（评论） @ ？（文章）
     * 2:评论的评论 带有指定的父级评论对象 存在回复的用户 ？（评论） @ ？（评论）
     */
    private Integer commentLevel;

    /**
     * 评论的父级id
     * 1、当评论的类型为1时，pid指代文章id
     * 2、当评论的类型为2时，pid指代评论id
     */
    private Integer pid;

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
