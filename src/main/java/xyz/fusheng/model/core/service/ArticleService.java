package xyz.fusheng.model.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.Article;
import xyz.fusheng.model.core.entity.ModelPlus;
import xyz.fusheng.model.core.vo.ArticleVo;

/**
 * @FileName: ArticleService
 * @Author: code-fusheng
 * @Date: 2020/5/14 14:04
 * @version: 1.0
 * Description: 文章业务逻辑接口
 */

public interface ArticleService extends IService<Article> {
    /**
     * 多条件分页查询
     * @param page
     * @return
     */
    Page<ArticleVo> getByPage(Page<ArticleVo> page);
}
