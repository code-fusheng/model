package xyz.fusheng.model.controller;

import com.alibaba.fastjson.JSON;
import org.apache.poi.ss.formula.functions.T;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.fusheng.model.common.enums.ResultEnums;
import xyz.fusheng.model.common.utils.*;
import xyz.fusheng.model.core.entity.Article;
import xyz.fusheng.model.core.service.ArticleService;
import xyz.fusheng.model.core.vo.ArticleVo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @FileName: ArticleController
 * @Author: code-fusheng
 * @Date: 2020/5/14 14:01
 * @version: 1.0
 * Description: 文章控制类
 */

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    //面向对象操作
    @Autowired
    @Qualifier("restHighLevelClient")  //如定义的名称与配置文件一直则不需要这个
    private RestHighLevelClient client;

    /**
     * 保存文章 - 增
     *
     * @param article
     * @return
     */
    @PostMapping("/save")
    public Result<Object> save(@RequestBody Article article) throws IOException {
        article.setAuthorId(SecurityUtil.getUserId());
        articleService.saveArticleAndUpdateCategory(article);
        return new Result<>("操作成功: 添加文章！");
    }

    /**
     * 根据id删除文章 - 删【逻辑删除】
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/deleteById/{id}")
    public Result<Object> deleteById(@PathVariable("id") Long id) throws IOException {
        articleService.deleteById(id);
        return new Result<>("操作成功: 删除文章！");
    }

    /**
     * 修改文章 - 改
     *
     * @param article
     * @return
     */
    @PutMapping("/update")
    public Result<Object> update(@RequestBody Article article) throws IOException {
        // 修改时先查询数据 获取 version 字段
        article.setVersion(articleService.getById(article.getArticleId()).getVersion());
        articleService.updateArticleAndCategory(article);
        return new Result<>("操作成功: 更新文章!");
    }

    /**
     * 多条件分页查询 - 查
     * @param page
     * @return
     */
    @PostMapping("/getByPage")
    public Result<Page<ArticleVo>> getByPage(@RequestBody Page<ArticleVo> page){
        String sortColumn = page.getSortColumn();
        String newSortColumn = StringUtils.upperCharToUnderLine(sortColumn);
        page.setSortColumn(newSortColumn);
        if(StringUtils.isNotBlank(sortColumn)){
            // 文章标题
            String[] sortColumns = {"article_title", "author_name", "good_count", "read_count", "collection_count", "comment_count", "created_time", "update_time"};
            List<String> sortList = Arrays.asList(sortColumns);
            if(!sortList.contains(newSortColumn.toLowerCase())) {
                return new Result<>(ResultEnums.ERROR.getCode(),"操作失败: 参数错误！");
            }
        }
        page = articleService.getByPage(page);
        return new Result<>("操作成功: 分页查询文章！", page);
    }

    /**
     * 查询所有 - 查
     * @param
     * @return
     */
    @GetMapping("/list")
    public Result<List<ArticleVo>> list(){
        List<ArticleVo> articleVoList = articleService.getList();
        return new Result<>("操作成功: 分页查询文章！", articleVoList);
    }

    /**
     * 根据id查询 - 查
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public Result<ArticleVo> getById(@PathVariable("id") Long id){
        ArticleVo articleVo = articleService.getById(id);
        return new Result<>("操作成功: 查询文章！", articleVo);
    }


    /**
     * 启用 - 改
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/enable/{id}")
    public Result<Object> enable(@PathVariable("id") Long id) throws IOException {
        articleService.enableById(id);
        return new Result<>("操作成功: 启用文章!");
    }

    /**
     * 弃用 - 改
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/disable/{id}")
    public Result<Object> disable(@PathVariable("id") Long id) throws IOException {
        articleService.disableById(id);
        return new Result<>("操作成功: 弃用文章！");
    }

    /**
     * 根据id阅读 - 查/改
     * @param id
     * @return
     */
    @GetMapping("/read/{id}")
    public Result<ArticleVo> read(@PathVariable("id") Long id){
        ArticleVo articleVo = articleService.readById(id);
        return new Result<>("操作成功: 阅读文章！", articleVo);
    }
}
