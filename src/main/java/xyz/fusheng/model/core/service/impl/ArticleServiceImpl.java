package xyz.fusheng.model.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.Article;
import xyz.fusheng.model.core.entity.ModelPlus;
import xyz.fusheng.model.core.mapper.ArticleMapper;
import xyz.fusheng.model.core.service.ArticleService;
import xyz.fusheng.model.core.vo.ArticleVo;

import javax.annotation.Resource;
import java.util.List;

/**
 * @FileName: ArticleServiceImpl
 * @Author: code-fusheng
 * @Date: 2020/5/14 14:05
 * @version: 1.0
 * Description: 文章业务逻辑接口实现类
 */

@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public Page<ArticleVo> getByPage(Page<ArticleVo> page) {
        // 查询数据
        List<ArticleVo> articleVoList = articleMapper.getByPage(page);
        page.setList(articleVoList);
        // 统计总数
        int totalCount = articleMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        return page;
    }
}
