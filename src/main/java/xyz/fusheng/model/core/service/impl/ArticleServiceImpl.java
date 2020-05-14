package xyz.fusheng.model.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.fusheng.model.common.enums.StateEnums;
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

    @Override
    public void deleteById(Integer id) {
        UpdateWrapper<Article> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(Article::getArticleId, id);
        updateWrapper.lambda().set(Article::getIsDeleted, StateEnums.DELETED.getCode());
        articleMapper.update(null, updateWrapper);
    }

    @Override
    public void enableById(Integer id) {
        UpdateWrapper<Article> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(Article::getArticleId, id);
        updateWrapper.lambda().set(Article::getIsEnabled, StateEnums.ENABLED.getCode());
        articleMapper.update(null, updateWrapper);
    }

    @Override
    public void disableById(Integer id) {
        UpdateWrapper<Article> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(Article::getArticleId, id);
        updateWrapper.lambda().set(Article::getIsEnabled, StateEnums.NOT_ENABLE.getCode());
        articleMapper.update(null, updateWrapper);
    }
}
