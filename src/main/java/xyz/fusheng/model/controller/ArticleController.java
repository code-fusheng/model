package xyz.fusheng.model.controller;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.fusheng.code.springboot.core.entity.ResultVo;
import xyz.fusheng.model.common.aspect.annotation.Log;
import xyz.fusheng.model.common.aspect.enums.BusinessType;
import xyz.fusheng.model.common.enums.ResultEnums;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.common.utils.SecurityUtil;
import xyz.fusheng.model.common.utils.StringUtils;
import xyz.fusheng.model.core.entity.Article;
import xyz.fusheng.model.core.service.ArticleService;
import xyz.fusheng.model.core.vo.ArticleVo;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/article/save','article:list:add')")
    @PostMapping("/save")
    @Log(title = "添加文章", businessType = BusinessType.INSERT)
    public ResultVo<Object> save(@RequestBody Article article) throws IOException {
        article.setAuthorId(SecurityUtil.getUserId());
        articleService.saveArticleAndUpdateCategory(article);
        return new ResultVo<>("操作成功: 添加文章！");
    }

    /**
     * 根据id删除文章 - 删【逻辑删除】
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/article/deleteById','article:list:delete')")
    @DeleteMapping("/deleteById/{id}")
    @Log(title = "删除文章", businessType = BusinessType.DELETE)
    public ResultVo<Object> deleteById(@PathVariable("id") Long id) throws IOException {
        articleService.deleteById(id);
        return new ResultVo<>("操作成功: 删除文章！");
    }

    /**
     * 修改文章 - 改
     *
     * @param article
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/article/update','article:list:update')")
    @PutMapping("/update")
    @Log(title = "修改文章", businessType = BusinessType.UPDATE)
    public ResultVo<Object> update(@RequestBody Article article) throws IOException {
        // 修改时先查询数据 获取 version 字段
        article.setVersion(articleService.getById(article.getArticleId()).getVersion());
        articleService.updateArticleAndCategory(article);
        return new ResultVo<>("操作成功: 更新文章!");
    }

    /**
     * 多条件分页查询 - 查
     * @param page
     * @return
     */
    @PostMapping("/getByPage")
    public ResultVo<Page<ArticleVo>> getByPage(@RequestBody Page<ArticleVo> page){
        String sortColumn = page.getSortColumn();
        String newSortColumn = StringUtils.upperCharToUnderLine(sortColumn);
        page.setSortColumn(newSortColumn);
        if(StringUtils.isNotBlank(sortColumn)){
            // 文章标题
            String[] sortColumns = {"article_title", "author_name", "good_count", "read_count", "collection_count", "comment_count", "created_time", "update_time"};
            List<String> sortList = Arrays.asList(sortColumns);
            if (!sortList.contains(newSortColumn.toLowerCase())) {
                return new ResultVo<>(ResultEnums.ERROR.getCode(), "操作失败: 参数错误！");
            }
        }
        page = articleService.getByPage(page);
        return new ResultVo<>("操作成功: 分页查询文章！", page);
    }

    /**
     * 查询所有 - 查【后台查询】
     *
     * @param
     * @return
     */
    @GetMapping("/getAll")
    public ResultVo<List<ArticleVo>> getAll() {
        List<ArticleVo> articleVoList = articleService.getList();
        return new ResultVo<>("操作成功: 分页查询文章！", articleVoList);
    }

    /**
     * 查询所有 - 查【前台查询】
     *
     * @param
     * @return
     */
    @GetMapping("/getList")
    public ResultVo<List<ArticleVo>> getList() {
        List<ArticleVo> articleVoList = articleService.getList();
        return new ResultVo<>("操作成功: 分页查询文章！", articleVoList);
    }

    /**
     * 根据id查询 - 查
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    @Log(title = "查询文章详情", businessType = BusinessType.SELECT)
    public ResultVo<ArticleVo> getById(@PathVariable("id") Long id){
        ArticleVo articleVo = articleService.getById(id);
        return new ResultVo<>("操作成功: 查询文章！", articleVo);
    }


    /**
     * 启用 - 改
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/article/enable','article:list:enable')")
    @PutMapping("/enable/{id}")
    @Log(title = "启用文章", businessType = BusinessType.ENABLE)
    public ResultVo<Object> enable(@PathVariable("id") Long id) throws IOException {
        articleService.enableById(id);
        return new ResultVo<>("操作成功: 启用文章!");
    }

    /**
     * 弃用 - 改
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/article/disable','article:list:disable')")
    @PutMapping("/disable/{id}")
    @Log(title = "弃用文章", businessType = BusinessType.DISABLE)
    public ResultVo<Object> disable(@PathVariable("id") Long id) throws IOException {
        articleService.disableById(id);
        return new ResultVo<>("操作成功: 弃用文章！");
    }

    /**
     * 根据id阅读 - 查/改
     * @param id
     * @return
     */
    @GetMapping("/read/{id}")
    @Log(title = "阅读文章", businessType = BusinessType.READ)
    public ResultVo<ArticleVo> read(@PathVariable("id") Long id){
        ArticleVo articleVo = articleService.readById(id);
        return new ResultVo<>("操作成功: 阅读文章！", articleVo);
    }

    /**
     * 查询上一篇与下一篇文章信息
     * @param id
     * @return
     */
    @GetMapping("/getLastAndNextArticleVo/{id}")
    public ResultVo<List<ArticleVo>> getLastAndNextArticleVo (@PathVariable("id") Long id) {
        List<ArticleVo> articleVoList = articleService.getLastAndNextArticleVoList(id);
        return new ResultVo<>("操作成功: 查询上一篇与下一篇文章信息", articleVoList);
    }

}
