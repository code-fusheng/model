package xyz.fusheng.model.controller;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.fusheng.model.common.enums.ResultEnums;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.common.utils.Result;
import xyz.fusheng.model.common.utils.SecurityUtil;
import xyz.fusheng.model.common.utils.StringUtils;
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

    /**
     * 保存文章 - 增
     * @param article
     * @return
     */
    @PostMapping("/save")
    public Result<Object> save(@RequestBody Article article){
        article.setArticleAuthor(SecurityUtil.getUserName());
        articleService.save(article);
        return new Result<>("操作成功: 添加文章！");
    }

    /**
     * 根据id删除文章 - 删【逻辑删除】
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result<Object> deleteById(@PathVariable("id") Long id){
        articleService.deleteById(id);
        return new Result<>("操作成功: 删除文章！");
    }

    /**
     * 修改文章 - 改
     * @param article
     * @return
     */
    @PutMapping("/update")
    public Result<Object> update(@RequestBody Article article){
        articleService.updateById(article);
        return new Result<>("操作成功: 更新文章!");
    }

    /**
     * 多条件分页查询 - 查
     * @param page
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/getByPage")
    public Result<Page<ArticleVo>> getByPage(@RequestBody Page<ArticleVo> page){
        String sortColumn = page.getSortColumn();
        String newSortColumn = StringUtils.upperCharToUnderLine(sortColumn);
        page.setSortColumn(newSortColumn);
        if(StringUtils.isNotBlank(sortColumn)){
            // 文章标题
            String[] sortColumns = {"article_title", "article_author", "good_count", "read_count", "collection_count", "comment_count", "created_time", "update_time"};
            List<String> sortList = Arrays.asList(sortColumns);
            if(!sortList.contains(newSortColumn.toLowerCase())) {
                return new Result<>(ResultEnums.ERROR.getCode(),"操作失败: 参数错误！");
            }
        }
        page = articleService.getByPage(page);
        return new Result<>("操作成功: 分页查询文章！", page);
    }

    /**
     * 启用 - 改
     * @param id
     * @return
     */
    @PutMapping("/enable/{id}")
    public Result<Object> enable(@PathVariable("id") Long id) {
        articleService.enableById(id);
        return new Result<>("操作成功: 启用文章!");
    }

    /**
     * 弃用 - 改
     * @param id
     * @return
     */
    @PutMapping("/disable/{id}")
    public Result<Object> disable(@PathVariable("id") Long id) {
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

    /**
     * 高亮搜索
     * @return
     * @throws IOException
     */
    @GetMapping("/highLightSearch")
    public Result<Page<Article>> search(@RequestBody Page page) throws IOException {
        String keyword = page.getKeyword();
        int pageNo = page.getCurrentPage();
        int pageSize = page.getPageSize();
        SearchResponse searchResponse = articleService.searchPageHighlightBuilder(keyword,pageNo,pageSize);
        //解析结果
        ArrayList<Map<String,Object>> list = new ArrayList<>();
        for (SearchHit doc:searchResponse.getHits().getHits()){
            // 解析高亮字段
            Map<String, HighlightField> highlightFields = doc.getHighlightFields();
            HighlightField articleTitle = highlightFields.get("articleTitle");
            // 解析原来的结果
            Map<String, Object> sourceAsMap = doc.getSourceAsMap();
            if(articleTitle!=null){
                Text[] fragments = articleTitle.fragments();
                String n_articleTitle="";
                for (Text res:fragments){
                    n_articleTitle += res;
                }
                //将原来的替换为高亮的
                sourceAsMap.put("articleTitle",n_articleTitle);
            }
            list.add(sourceAsMap);
        }
        page.setList(list);
        return new Result<>(page);
    }

}
