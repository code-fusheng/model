package xyz.fusheng.model;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.fusheng.model.core.entity.Article;
import xyz.fusheng.model.core.service.ArticleService;
import xyz.fusheng.model.core.vo.ArticleVo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class ElasticsearchTests {

    @Autowired
    private ArticleService articleService;

    @Test
    void contextLoads() {
    }

    //面向对象操作
    @Autowired
    @Qualifier("restHighLevelClient")  //如定义的名称与配置文件一直则不需要这个
    private RestHighLevelClient client;

    //测试索引的创建
    @Test
    void testCreateIndex() throws IOException {

        //1.创建索引请求   类似于 kibana 中的put命令
        CreateIndexRequest request = new CreateIndexRequest("code_fusheng");
        //2.客户端执行创建请求 IndicesClient,请求后获得响应
        CreateIndexResponse createIndexResponse =
                client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);
    }

    //测试获取索引 判断其是否存在
    @Test
    void testExistIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("code_fusheng");
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    //删除索引
    @Test
    void testDeleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("code_fusheng");
        AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(delete.isAcknowledged());   //判断是否删除成功
    }

    //测试添加文档
    @Test
    void testAddDocument() throws IOException {
        // 1、创建对象
        Article article = articleService.getById(6L);
        // 2、创建请求
        IndexRequest request = new IndexRequest("article_index");
        // 3、设置规则 => put /article_index/_doc/1
        request.id("2");
        // 4、设置超时
        request.timeout(TimeValue.timeValueSeconds(1));
        request.timeout("5s");
        // 5、将数据放入请求
        request.source(JSON.toJSONString(article), XContentType.JSON);
        // 6、客户端发送请求。获取响应结果
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);

        System.out.println(indexResponse.toString());
        System.out.println(indexResponse.status());
    }

    //获取文档信息
    @Test
    void testGetDocument() throws IOException {
        GetRequest request = new GetRequest("article_index", "2");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        // 打印文档内容
        System.out.println(response.getSourceAsString());
        System.out.println(response);
    }

    //更新文档信息
    @Test
    void testUpdateDocument() throws IOException {
        // 获取请求
        UpdateRequest request = new UpdateRequest("article_index", "1");
        request.timeout("5s");

        // 修改数据
        Article article = articleService.getById(6L);
        article.setArticleTitle("Elasticsearch测试修改内容");
        articleService.updateById(article);
        request.doc(JSON.toJSONString(article), XContentType.JSON);

        // 客户端执行请求
        UpdateResponse update = client.update(request, RequestOptions.DEFAULT);
        System.out.println(update.status());
    }

    //删除文档信息
    @Test
    void testDeleteDocument() throws IOException {
        DeleteRequest request = new DeleteRequest("article_index", "2");
        request.timeout("5s");
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    //查询
    @Test
    void testSearch() throws IOException {
        SearchRequest searchRequest = new SearchRequest("article_index");
        //构建查询条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        /**
         * 查询条件可以使用QueryBuilders工具来实现
         *  eg:
         *      QueryBuilders.termQuery() 精确匹配
         *      QueryBuilders.matchAllQuery() 匹配所有
         *      ......
         */
        // TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("articleCategory",1);
        // TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("articleTitle.keyword","文章");
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("articleTitle", "测试");
        //封装查询条件
        // sourceBuilder.query(termQueryBuilder);
        sourceBuilder.query(matchQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS)); //设置超时
        searchRequest.source(sourceBuilder);
        // 客户端发送请求
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        // 将json转换为字符串打印
        System.out.println(JSON.toJSONString(searchResponse.getHits()));
        System.out.println("======遍历======");
        for(SearchHit res : searchResponse.getHits().getHits()){
            System.out.println(res.getSourceAsMap());
        }
    }

    @Test
    void searchHighlightBuilder() throws IOException {
        String keyword = "文章";
        int pageNo = 1;
        int pageSize = 5;
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
        // 7、测试输出
        System.out.println(searchResponse);
        //解析结果
        ArrayList<Map<String,Object>> list = new ArrayList<>();
        for (SearchHit doc:searchResponse.getHits().getHits()){
            // 解析高亮字段
            Map<String, HighlightField> highlightFields = doc.getHighlightFields();
            HighlightField articleTitle = highlightFields.get("articleTitle");
            // 解析原来的结果
            Map<String, Object> sourceAsMap = doc.getSourceAsMap();  //原来的结果
            if(articleTitle!=null){
                Text[] fragments = articleTitle.fragments();
                String n_articleTitle="";
                for (Text res:fragments){
                    n_articleTitle += res;
                }
                sourceAsMap.put("articleTitle",n_articleTitle);  //将原来的替换为高亮的
            }
            list.add(sourceAsMap);
        }
        System.out.println(list);
    }

    //批量处理数据(全量同步)
    @Test
    void testBulkRequest() throws IOException {
        BulkRequest request = new BulkRequest();
        request.timeout("10s");
        // 获取数据库的article数据
        List<ArticleVo> articleVoList = articleService.getList();
        for(ArticleVo articleVo : articleVoList) {
            request.add(
                    new IndexRequest("model_article_index")
                            .id(""+articleVo.getArticleId())
                            .source(JSON.toJSONString(articleVo), XContentType.JSON)
            );
        }
        // 客户端执行请求
        BulkResponse responses = client.bulk(request, RequestOptions.DEFAULT);
        // 返回是否失败状态
        System.out.println(responses.hasFailures());
    }
}
