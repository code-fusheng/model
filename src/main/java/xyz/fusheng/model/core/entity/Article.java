package xyz.fusheng.model.core.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @FileName: Article
 * @Author: code-fusheng
 * @Date: 2020/5/14 11:32
 * @version: 1.0
 * Description: 博客、文章实体
 */

@Data
@TableName("m_article")
public class Article implements Serializable {

    private static final long serialVersionUID = 5966746803753869693L;

    /**
     * 文章id
     */
    @TableId
    private Long articleId;

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 文章作者
     */
    private String articleAuthor;

    /**
     * 文章封面
     */
    private String articleImage;

    /**
     * 文章描述
     */
    private String articleDesc;

    /**
     * 文章内容
     */
    private String articleContent;

    /**
     * 编辑模式
     */
    private String editModel;

    /**
     * 文章类型 类型id
     */
    private Long articleCategory;

    /**
     * 点赞量
     */
    private Integer goodCount;

    /**
     * 阅读量
     */
    private Integer readCount;

    /**
     * 收藏量
     */
    private Integer collectionCount;

    /**
     * 评论量
     */
    private Integer commentCount;

    /**
     * 文章来源
     */
    private String articleSource;

    /**
     * 文章创建时间
     */
    private String createdTime;

    /**
     * 文章修改时间
     */
    private String updateTime;

    /**
     * 乐观锁
     */
    private Integer version;

    /**
     * 是否启用，0否1是 默认1
     */
    private Integer isEnabled;

    /**
     * 是否删除，0否1是 默认0
     */
    private Integer isDeleted;

}
