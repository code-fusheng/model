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
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.fusheng.model.common.utils.SearchPage;
import xyz.fusheng.model.core.entity.Article;
import xyz.fusheng.model.core.service.ArticleService;
import xyz.fusheng.model.core.vo.ArticleVo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
        CreateIndexRequest request = new CreateIndexRequest("model_article_index");
        //2.客户端执行创建请求 IndicesClient,请求后获得响应
        CreateIndexResponse createIndexResponse =
                client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);
    }

    //测试获取索引 判断其是否存在
    @Test
    void testExistIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("model_article_index");
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    //删除索引
    @Test
    void testDeleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("model_article_index");
        AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(delete.isAcknowledged());   //判断是否删除成功
    }

    //测试添加文档
    @Test
    void testAddDocument() throws IOException {
        // 1、创建对象
        Article article = new Article();
        article.setArticleTitle("Elasticsearch测试文章标题 - Java 测试");
        article.setArticleDesc("这是Elasticsearch测试文章的第二篇 -  Java 测试");
        article.setArticleId(2L);
        String id = article.getArticleId().toString();
        // 2、创建请求
        IndexRequest request = new IndexRequest("model_article_index");
        // 3、设置规则 => put /model_article_index/_doc/1
        request.id(id);
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
        GetRequest request = new GetRequest("model_article_index", "2");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        // 打印文档内容
        System.out.println(response.getSourceAsString());
        System.out.println(response);
    }

    //更新文档信息
    @Test
    void testUpdateDocument() throws IOException {
        // 获取请求
        UpdateRequest request = new UpdateRequest("model_article_index", "1");
        request.timeout("5s");
        // 修改数据
        Article article = new Article();
        article.setArticleTitle("Elasticsearch测试修改内容");
        article.setArticleDesc("这是在Java中测试Elasticsearch更新文章的测试");
        request.doc(JSON.toJSONString(article), XContentType.JSON);
        // 客户端执行请求
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    //删除文档信息
    @Test
    void testDeleteDocument() throws IOException {
        DeleteRequest request = new DeleteRequest("model_article_index", "2");
        request.timeout("5s");
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    //查询
    @Test
    void testSearch() throws IOException {
        SearchRequest searchRequest = new SearchRequest("model_article_index");
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
        for (SearchHit res : searchResponse.getHits().getHits()) {
            System.out.println(res.getSourceAsMap());
        }
    }

    /**
     * 单字段高亮查询
     *
     * @throws IOException
     */
    @Test
    void searchHighlightBuilder() throws IOException {
        String keyword = "测试";
        int pageNo = 1;
        int pageSize = 5;
        if (pageNo <= 1) {
            pageNo = 1;
        }
        // 1、创建查询索引
        SearchRequest searchRequest = new SearchRequest("model_article_index");
        // 2、条件查询
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 3、分页
        sourceBuilder.from((pageNo - 1) * pageSize);
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
                String n_articleTitle = "";
                for (Text res : fragments) {
                    n_articleTitle += res;
                }
                sourceAsMap.put("articleTitle", n_articleTitle);  //将原来的替换为高亮的
            }
            list.add(sourceAsMap);
        }
        System.out.println(list);
    }

    /**
     * 多字段高亮搜索
     *
     * @throws IOException
     */
    @Test
    void searchHighlightWithFields() throws IOException {
        SearchPage searchPage = new SearchPage();
        searchPage.setPageNo(1);
        searchPage.setPageSize(1);
        searchPage.setIndex("model_article_index");
        searchPage.setKeyword("数据结构");
        searchPage.setParams("articleTitle");
        String[] fields = {"articleTitle", "articleDesc"};
        searchPage.setKeyFields(fields);
        // 1、创建查询索引
        SearchRequest searchRequest = new SearchRequest(searchPage.getIndex());
        // 2、条件查询
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 3、分页
        sourceBuilder.from((searchPage.getPageNo() - 1) * searchPage.getPageSize());
        sourceBuilder.size(searchPage.getPageSize());
        // 4、精准匹配(中文) 匹配查询构建器
        // MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(searchPage.getKeyFields()[0], searchPage.getKeyword());
        // 多匹配查询构建器
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(searchPage.getKeyword(), searchPage.getKeyFields());
        // multiMatchQueryBuilder.
        // .field("analyzer", "ik").field("search_analyzer","ik_smart")
        // 默认分词 - 按词切分
        multiMatchQueryBuilder.analyzer("standard");
        // 不分词 - 不切分
        // multiMatchQueryBuilder.analyzer("keyword");
        // sourceBuilder.query(matchQueryBuilder);
        sourceBuilder.query(multiMatchQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        // 5、高亮设置(替换返回结果文本中目标值的文本内容)
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        // highlightBuilder.field(searchPage.getKeyFields()[0]).field(searchPage.getKeyFields()[1]);
        for (int i = 0; i < searchPage.getKeyFields().length; i++) {
            highlightBuilder.field(searchPage.getKeyFields()[i]);
        }
        highlightBuilder.requireFieldMatch(true);
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        sourceBuilder.highlighter(highlightBuilder);
        // 6、执行搜索
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        // 7、测试输出
        // System.out.println(searchResponse);
        //解析结果
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit doc : searchResponse.getHits().getHits()) {
            // 解析高亮字段
            Map<String, HighlightField> highlightFields = doc.getHighlightFields();

            for (int i = 0; i < searchPage.getKeyFields().length; i++) {
                HighlightField fieldTitle = highlightFields.get(searchPage.getKeyFields()[i]);
                // 获取原来的结果集
                Map<String, Object> sourceAsMap = doc.getSourceAsMap();
                if (fieldTitle != null) {
                    // 获取内容中匹配的片段
                    Text[] fragments = fieldTitle.fragments();
                    // 设置当前的目标字段为空
                    String new_fieldTitle = "";
                    for (Text res : fragments) {
                        new_fieldTitle += res;
                    }
                    // 将原来的结果替换为新结果
                    sourceAsMap.put(searchPage.getKeyFields()[i], new_fieldTitle);
                }
                list.add(sourceAsMap);
            }
        }
        // List 数组去重， 多字段查询高亮解析的时候存在数组重复的情况（优化方法未知！）
        List newList = list.stream().distinct().collect(Collectors.toList());
        searchPage.setList(newList);
        System.out.println(searchPage);
    }

    //批量处理数据(全量同步)
    @Test
    void testBulkRequest() throws IOException {
        DeleteIndexRequest indexRequest = new DeleteIndexRequest("model_article_index");
        client.indices().delete(indexRequest, RequestOptions.DEFAULT);
        BulkRequest request = new BulkRequest();
        request.timeout("10s");
        // 获取数据库的article数据
        List<ArticleVo> articleVoList = articleService.getList();
        for (ArticleVo articleVo : articleVoList) {
            request.add(
                    new IndexRequest("model_article_index")
                            .id("" + articleVo.getArticleId())
                            .source(JSON.toJSONString(articleVo), XContentType.JSON)
            );
        }
        // 客户端执行请求
        BulkResponse responses = client.bulk(request, RequestOptions.DEFAULT);
        // 返回是否失败状态
        System.out.println(responses.hasFailures());
    }

    /**
     * matchAllQuery 查询
     * 查询所有
     */
    @Test
    void matchAllQuery() throws IOException {
        // 指定索引 可以指定多个
        SearchRequest searchRequest = new SearchRequest("model_article_index");
        // 创建搜索源构建器（设置分页、高亮、目标字段）
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchRequest.source(searchSourceBuilder);
        // 设置分页起始位置
        searchSourceBuilder.from(0);
        // 设置分页大小
        searchSourceBuilder.size(2);
        // 定义搜索响应
        SearchResponse searchResponse = null;
        searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        searchResponse.getHits().forEach(System.out::println);
    }

    /**
     * matchQuery 匹配查询
     */
    @Test
    void matchQuery() throws IOException {
        SearchRequest searchRequest = new SearchRequest("model_article_index");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchRequest.source(searchSourceBuilder);
        // MatchQueryBuilder 匹配查询构建器
        // QueryBuilders 查询生成器
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("articleTitle", "文章");
        // 搜索源工厂通过匹配查询构建器的条件执行查询
        searchSourceBuilder.query(matchQueryBuilder);
        // 模糊查询条件
        matchQueryBuilder.fuzziness(Fuzziness.AUTO);
        // 前缀查询的长度
        matchQueryBuilder.prefixLength(3);
        // maxExpansion 选项、用来控制模糊查询
        matchQueryBuilder.maxExpansions(10);
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(10);
        SearchResponse searchResponse = null;
        searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        searchResponse.getHits().forEach(System.out::println);
    }

    /**
     * termQuery 项查询
     */
    @Test
    void termQuery() throws IOException {
        SearchRequest searchRequest = new SearchRequest("model_article_index");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchRequest.source(searchSourceBuilder);
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(10);
        // termQuery 项查询
        searchSourceBuilder.query(QueryBuilders.termQuery("articleTitle", "测试"));
        SearchResponse searchResponse = null;
        searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        searchResponse.getHits().forEach(System.out::println);
    }

    /**
     * 聚合查询
     */
    @Test
    void aggregation() {
        SearchRequest searchRequest = new SearchRequest("model_article_index");
        // 略
    }
}
