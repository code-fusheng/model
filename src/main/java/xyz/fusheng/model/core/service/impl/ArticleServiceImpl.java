package xyz.fusheng.model.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.fusheng.model.common.enums.StateEnums;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.common.utils.SecurityUtil;
import xyz.fusheng.model.core.entity.*;
import xyz.fusheng.model.core.mapper.*;
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

@Slf4j
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private GoodMapper goodMapper;

    @Resource
    private CollectionMapper collectionMapper;

    //面向对象操作
    @Autowired
    @Qualifier("restHighLevelClient")  //如定义的名称与配置文件一直则不需要这个
    private RestHighLevelClient client;

    /**
     * 添加文章 并且更新对应分类的文章数 失败回滚
     *
     * @param article
     */
    @Override
    //@Transactional(rollbackFor = Exception.class)
    public void saveArticleAndUpdateCategory(Article article) {
        articleMapper.insert(article);
        ArticleVo articleDoc = articleMapper.getById(article.getArticleId());
        IndexResponse indexResponse = null;
        try {
            IndexRequest request = new IndexRequest("model_article_index");
            request.id(article.getArticleId().toString());
            request.timeout("5s");
            request.source(JSON.toJSONString(articleDoc), XContentType.JSON);
            indexResponse = client.index(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("同步新增文章:{}失败,异常信息:{}", article.getArticleTitle(), e);
        }
        // 添加成功时，更新对应分类文章数
        long categoryId = article.getArticleCategory();
        // 根据分类id 查询该分类的文章数
        int categoryCountForArticle = categoryMapper.getArticleCountByCategoryId(categoryId);
        // 更新分类文章数
        Category category = categoryMapper.selectById(categoryId);
        category.setArticleCount(categoryCountForArticle);
        categoryMapper.updateById(category);
    }

    @Override
    //@Transactional(rollbackFor = Exception.class)
    public void updateArticleAndCategory(Article article) {
        // 获取article的旧/新分类
        long oldCategoryId = articleMapper.getById(article.getArticleId()).getArticleCategory();
        long newCategoryId = article.getArticleCategory();
        // 判断是否更改了分类
        if(oldCategoryId != newCategoryId) {
            // 更新 旧分类 文章数
            Category oldCategory = categoryMapper.selectById(oldCategoryId);
            oldCategory.setArticleCount(oldCategory.getArticleCount() - 1);
            categoryMapper.updateById(oldCategory);
            // 更新 新分类 文章数
            Category newCategory = categoryMapper.selectById(newCategoryId);
            newCategory.setArticleCount(newCategory.getArticleCount() + 1);
            categoryMapper.updateById(newCategory);
        }
        articleMapper.updateById(article);
        ArticleVo articleDoc = articleMapper.getById(article.getArticleId());
        UpdateResponse updateResponse = null;
        try {
            UpdateRequest request = new UpdateRequest("model_article_index", articleDoc.getArticleId().toString());
            request.timeout("5s");
            request.doc(JSON.toJSONString(articleDoc), XContentType.JSON);
            updateResponse = client.update(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("同步更新文章:{}失败,异常信息:{}", article.getArticleTitle(), e);
        }
    }

    @Override
    public Page<ArticleVo> getByPage(Page<ArticleVo> page) {
        // 查询数据
        List<ArticleVo> articleVoList = articleMapper.getByPage(page);
        page.setList(articleVoList);
        // 判断当前用户是否登录
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            articleVoList.forEach(articleVo -> {
                // 用户浏览界面文章是否点赞标识
                List<Good> goods = goodMapper.getGoodsByUserAndTargetAndType(SecurityUtil.getUserId(), articleVo.getArticleId(), StateEnums.ARTICLE_GOOD.getCode());
                if (goods.size() == 0) {
                    articleVo.setGoodArticleFlag(false);
                } else {
                    articleVo.setGoodArticleFlag(true);
                }
                // 用户浏览界面文章是否收藏标识
                List<Collection> collections = collectionMapper.getCollectionsByUserAndTargetAndType(SecurityUtil.getUserId(), articleVo.getArticleId(), StateEnums.ARTICLE_COLLECTION.getCode());
                if (collections.size() == 0) {
                    articleVo.setCollectionArticleFlag(false);
                } else {
                    articleVo.setCollectionArticleFlag(true);
                }
            });
        }

        // 统计总数
        int totalCount = articleMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        return page;
    }

    @Override
    public List<ArticleVo> getList() {
        return articleMapper.getList();
    }

    @Override
    public ArticleVo getById(Long id) {
        return articleMapper.getById(id);
    }

    @Override
    public void deleteById(Long id) {
        Article article = articleMapper.selectById(id);
        articleMapper.deleteById(id);
        DeleteResponse deleteResponse = null;
        try {
            DeleteRequest request = new DeleteRequest("model_article_index", id.toString());
            request.timeout("5s");
            deleteResponse = client.delete(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("同步删除文章:{}失败,异常信息:{}", article.getArticleTitle(), e);
        }
    }

    @Override
    public void enableById(Long id) {
        UpdateWrapper<Article> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(Article::getArticleId, id);
        updateWrapper.lambda().set(Article::getIsEnabled, StateEnums.ENABLED.getCode());
        articleMapper.update(null, updateWrapper);
        ArticleVo articleDoc = articleMapper.getById(id);
        IndexResponse indexResponse = null;
        try {
            IndexRequest request = new IndexRequest("model_article_index");
            request.id(articleDoc.getArticleId().toString());
            request.timeout("5s");
            request.source(JSON.toJSONString(articleDoc), XContentType.JSON);
            indexResponse = client.index(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("同步启用文章:{}失败,异常信息:{}", articleDoc.getArticleTitle(), e);
        }
    }

    @Override
    public void disableById(Long id) {
        Article article = articleMapper.selectById(id);
        UpdateWrapper<Article> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(Article::getArticleId, id);
        updateWrapper.lambda().set(Article::getIsEnabled, StateEnums.NOT_ENABLE.getCode());
        articleMapper.update(null, updateWrapper);
        DeleteRequest request = new DeleteRequest("model_article_index", id.toString());
        request.timeout("5s");
        DeleteResponse deleteResponse = null;
        try {
            deleteResponse = client.delete(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("同步删除文章:{}失败,异常信息:{}", article.getArticleTitle(), e);
        }
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
        // 根据作者id 查询头像、作者名
        User user = userMapper.selectById(article.getAuthorId());
        articleVo.setAuthorName(user.getUsername());
        articleVo.setHeader(user.getHeader());
        // 判断当前用户是否登录
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            // 用户浏览界面文章是否点赞标识
            List<Good> goods = goodMapper.getGoodsByUserAndTargetAndType(SecurityUtil.getUserId(), articleVo.getArticleId(), StateEnums.ARTICLE_GOOD.getCode());
            if (goods.size() == 0) {
                articleVo.setGoodArticleFlag(false);
            } else {
                articleVo.setGoodArticleFlag(true);
            }
            // 用户浏览界面文章是否收藏标识
            List<Collection> collections = collectionMapper.getCollectionsByUserAndTargetAndType(SecurityUtil.getUserId(), articleVo.getArticleId(), StateEnums.ARTICLE_COLLECTION.getCode());
            if (collections.size() == 0) {
                articleVo.setCollectionArticleFlag(false);
            } else {
                articleVo.setCollectionArticleFlag(true);
            }
        }
        return articleVo;
    }
}
