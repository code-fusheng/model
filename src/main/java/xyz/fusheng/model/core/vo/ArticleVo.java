package xyz.fusheng.model.core.vo;

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

}
