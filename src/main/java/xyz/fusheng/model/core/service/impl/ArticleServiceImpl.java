package xyz.fusheng.model.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import xyz.fusheng.model.common.enums.StateEnums;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.Article;
import xyz.fusheng.model.core.entity.Category;
import xyz.fusheng.model.core.mapper.ArticleMapper;
import xyz.fusheng.model.core.mapper.CategoryMapper;
import xyz.fusheng.model.core.service.ArticleService;
import xyz.fusheng.model.core.vo.ArticleVo;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @Resource
    private CategoryMapper categoryMapper;

    //面向对象操作
    @Autowired
    @Qualifier("restHighLevelClient")  //如定义的名称与配置文件一直则不需要这个
    private RestHighLevelClient client;

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
    public void deleteById(Long id) {
        UpdateWrapper<Article> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(Article::getArticleId, id);
        updateWrapper.lambda().set(Article::getIsDeleted, StateEnums.DELETED.getCode());
        articleMapper.update(null, updateWrapper);
    }

    @Override
    public void enableById(Long id) {
        UpdateWrapper<Article> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(Article::getArticleId, id);
        updateWrapper.lambda().set(Article::getIsEnabled, StateEnums.ENABLED.getCode());
        articleMapper.update(null, updateWrapper);
    }

    @Override
    public void disableById(Long id) {
        UpdateWrapper<Article> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(Article::getArticleId, id);
        updateWrapper.lambda().set(Article::getIsEnabled, StateEnums.NOT_ENABLE.getCode());
        articleMapper.update(null, updateWrapper);
    }

    @Override
    public ArticleVo readById(Long id) {
        Article article = articleMapper.selectById(id);
        // 阅读时需要更新阅读数
        article.setReadCount(article.getReadCount()+1);
        articleMapper.updateById(article);
        // 将 article -> articleVo
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        // 查询分类
        Category category = categoryMapper.selectById(article.getArticleCategory());
        articleVo.setCategoryName(category.getCategoryName());
        return articleVo;
    }

    @Override
    public SearchResponse searchPageHighlightBuilder(String keyword, int pageNo, int pageSize) throws IOException {
        if(pageNo <= 1){ pageNo=1; }
        // 1、创建查询索引
        SearchRequest searchRequest = new SearchRequest("article_index");
        // 2、条件查询
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 3、分页
        sourceBuilder.from(pageNo);
        sourceBuilder.size(pageSize);
        // 4、精准匹配(中文)
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("articleTitle", keyword);
        sourceBuilder.query(matchQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        // 5、高亮设置(替换返回结果文本中目标值的文本内容)
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("articleTitle");
        highlightBuilder.requireFieldMatch(true);
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        sourceBuilder.highlighter(highlightBuilder);
        // 6、执行搜索
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        return searchResponse;
    }
}
