package xyz.fusheng.model.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.Article;
import xyz.fusheng.model.core.entity.Category;
import xyz.fusheng.model.core.vo.ArticleVo;

import java.util.List;

/**
 * @FileName: ArticleMapper
 * @Author: code-fusheng
 * @Date: 2020/5/14 14:06
 * @version: 1.0
 * Description: 文章Mapper
 */

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    /**
     * 多条件分页查询
     * @param page
     * @return
     */
    List<ArticleVo> getByPage(Page<ArticleVo> page);

    /**
     * 分页查询统计总数
     * @param page
     * @return
     */
    int getCountByPage(Page<ArticleVo> page);
}
