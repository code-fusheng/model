package xyz.fusheng.model.core.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import xyz.fusheng.model.core.entity.Article;

/**
 * @FileName: ArticleVo
 * @Author: code-fusheng
 * @Date: 2020/5/14 11:50
 * @version: 1.0
 * Description: 文章视图模型
 */

@Data
public class ArticleVo extends Article {

    /**
     * 文章类型名
     */
    private String categoryName;

    /**
     * 文章作者名
     */
    private String authorName;

    /**
     * 用户头像
     */
    private String header;

    /**
     * 文章点赞状态标识 只用于前端判断，不做数据存储
     */
    private boolean goodArticleFlag = false;

}
