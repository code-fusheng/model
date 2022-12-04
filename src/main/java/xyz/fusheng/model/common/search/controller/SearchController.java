// package xyz.fusheng.model.common.search.controller;
//
// import org.elasticsearch.action.search.SearchRequest;
// import org.elasticsearch.action.search.SearchResponse;
// import org.elasticsearch.client.RequestOptions;
// import org.elasticsearch.client.RestHighLevelClient;
// import org.elasticsearch.common.unit.TimeValue;
// import org.elasticsearch.index.query.MatchQueryBuilder;
// import org.elasticsearch.index.query.QueryBuilders;
// import org.elasticsearch.index.query.TermQueryBuilder;
// import org.elasticsearch.search.builder.SearchSourceBuilder;
// import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Qualifier;
// import org.springframework.web.bind.annotation.*;
// import xyz.fusheng.model.common.utils.Result;
// import xyz.fusheng.model.core.entity.Article;
//
// import java.io.IOException;
// import java.util.List;
// import java.util.Map;
// import java.util.concurrent.TimeUnit;
//
// /**
//  * @FileName: SearchController
//  * @Author: code-fusheng
//  * @Date: 2020/5/15 15:32
//  * @version: 1.0
//  * Description: 搜索控制类
//  */
//
// @RestController
// @RequestMapping("/search")
// public class SearchController {
//
//     @Autowired
//     @Qualifier("restHighLevelClient")
//     private RestHighLevelClient client;
//
//     /**
//      * 高亮文章搜索
//      * @param keyword
//      * @param pageNo
//      * @param pageSize
//      * @return ResultVo<List<Article>>
//      */
//     @PostMapping("/article")
//     public void searchHighlightBuilder(@RequestParam String keyword, @RequestParam int pageNo, @RequestParam int pageSize) throws IOException {
//         if(pageNo <= 1){ pageNo=1; }
//         // 1、创建查询索引
//         SearchRequest searchRequest = new SearchRequest("article_index");
//         // 2、条件查询
//         SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//         // 3、分页
//         sourceBuilder.from(pageNo);
//         sourceBuilder.size(pageSize);
//         // 4、精准匹配(中文)
//         MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("articleTitle", keyword);
//         sourceBuilder.query(matchQueryBuilder);
//         sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
//         // 5、高亮设置(替换返回结果文本中目标值的文本内容)
//         HighlightBuilder highlightBuilder = new HighlightBuilder();
//         highlightBuilder.field("articleTitle");
//         highlightBuilder.requireFieldMatch(true);
//         highlightBuilder.preTags("<span style='color:red'>");
//         highlightBuilder.postTags("</span>");
//         sourceBuilder.highlighter(highlightBuilder);
//         // 6、执行搜索
//         searchRequest.source(sourceBuilder);
//         SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//         // 7、测试输出
//         System.out.println(searchResponse);
//
//     }
//
//
// }
