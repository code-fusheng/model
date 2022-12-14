package xyz.fusheng.model.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @FileName: ArticleServiceImpl
 * @Author: code-fusheng
 * @Date: 2020/5/14 14:05
 * @version: 1.0
 * Description: ?????????????????????????????????
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

    //??????????????????
    @Autowired
    @Qualifier("restHighLevelClient")  //?????????????????????????????????????????????????????????
    private RestHighLevelClient client;

    /**
     * ???????????? ???????????????????????????????????? ????????????
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
            log.info("??????????????????:{}??????,????????????:{}", article.getArticleTitle(), e);
        }
        // ?????????????????????????????????????????????
        long categoryId = article.getArticleCategory();
        // ????????????id ???????????????????????????
        int categoryCountForArticle = categoryMapper.getArticleCountByCategoryId(categoryId);
        // ?????????????????????
        Category category = categoryMapper.selectById(categoryId);
        category.setArticleCount(categoryCountForArticle);
        categoryMapper.updateById(category);
    }

    @Override
    //@Transactional(rollbackFor = Exception.class)
    public void updateArticleAndCategory(Article article) {
        // ??????article??????/?????????
        long oldCategoryId = articleMapper.getById(article.getArticleId()).getArticleCategory();
        long newCategoryId = article.getArticleCategory();
        // ???????????????????????????
        if(oldCategoryId != newCategoryId) {
            // ?????? ????????? ?????????
            Category oldCategory = categoryMapper.selectById(oldCategoryId);
            oldCategory.setArticleCount(oldCategory.getArticleCount() - 1);
            categoryMapper.updateById(oldCategory);
            // ?????? ????????? ?????????
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
            log.info("??????????????????:{}??????,????????????:{}", article.getArticleTitle(), e);
        }
    }

    @Override
    public Page<ArticleVo> getByPage(Page<ArticleVo> page) {
        // ????????????
        List<ArticleVo> articleVoList = articleMapper.getByPage(page);
        page.setList(articleVoList);
        // ??????????????????????????????
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            articleVoList.forEach(articleVo -> {
                // ??????????????????????????????????????????
                List<Good> goods = goodMapper.getGoodsByUserAndTargetAndType(SecurityUtil.getUserId(), articleVo.getArticleId(), StateEnums.ARTICLE_GOOD.getCode());
                if (goods.size() == 0) {
                    articleVo.setGoodArticleFlag(false);
                } else {
                    articleVo.setGoodArticleFlag(true);
                }
                // ??????????????????????????????????????????
                List<Collection> collections = collectionMapper.getCollectionsByUserAndTargetAndType(SecurityUtil.getUserId(), articleVo.getArticleId(), StateEnums.ARTICLE_COLLECTION.getCode());
                if (collections.size() == 0) {
                    articleVo.setCollectionArticleFlag(false);
                } else {
                    articleVo.setCollectionArticleFlag(true);
                }
            });
        }

        // ????????????
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
            log.info("??????????????????:{}??????,????????????:{}", article.getArticleTitle(), e);
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
            log.info("??????????????????:{}??????,????????????:{}", articleDoc.getArticleTitle(), e);
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
            log.info("??????????????????:{}??????,????????????:{}", article.getArticleTitle(), e);
        }
    }

    @Override
    public ArticleVo readById(Long id) {
        Article article = articleMapper.selectById(id);
        // ??????????????????????????????
        article.setReadCount(article.getReadCount()+1);
        articleMapper.updateById(article);
        // ??? article -> articleVo
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        // ????????????
        Category category = categoryMapper.selectById(article.getArticleCategory());
        articleVo.setCategoryName(category.getCategoryName());
        // ????????????id ????????????????????????
        User user = userMapper.selectById(article.getAuthorId());
        articleVo.setAuthorName(user.getUsername());
        articleVo.setHeader(user.getHeader());
        // ??????????????????????????????
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            // ??????????????????????????????????????????
            List<Good> goods = goodMapper.getGoodsByUserAndTargetAndType(SecurityUtil.getUserId(), articleVo.getArticleId(), StateEnums.ARTICLE_GOOD.getCode());
            if (goods.size() == 0) {
                articleVo.setGoodArticleFlag(false);
            } else {
                articleVo.setGoodArticleFlag(true);
            }
            // ??????????????????????????????????????????
            List<Collection> collections = collectionMapper.getCollectionsByUserAndTargetAndType(SecurityUtil.getUserId(), articleVo.getArticleId(), StateEnums.ARTICLE_COLLECTION.getCode());
            if (collections.size() == 0) {
                articleVo.setCollectionArticleFlag(false);
            } else {
                articleVo.setCollectionArticleFlag(true);
            }
        }
        return articleVo;
    }

    @Override
    public List<ArticleVo> getLastAndNextArticleVoList(Long id) {
        ArticleVo centerArticle = articleMapper.getById(id);
        List<ArticleVo> articleVoList = new ArrayList<>();
        ArticleVo lastArticleVo = articleMapper.getLastArticle(id, centerArticle.getArticleCategory());
        ArticleVo nextArticleVo = articleMapper.getNextArticle(id, centerArticle.getArticleCategory());
        articleVoList.add(lastArticleVo);
        articleVoList.add(nextArticleVo);
        return articleVoList;
    }
}
