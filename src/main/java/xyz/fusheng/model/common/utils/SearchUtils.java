package xyz.fusheng.model.common.utils;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import xyz.fusheng.model.core.service.ArticleService;
import xyz.fusheng.model.core.vo.ArticleVo;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @FileName: SearchUtils
 * @Author: code-fusheng
 * @Date: 2020/5/28 0:16
 * @version: 1.0
 * Description: 搜索工具类
 * cron表达式语法
 * [秒] [分] [小时] [日] [月] [周] [年]
 * 注：[年]不是必须的域，可以省略[年]，则一共6个域
 */

@Component
public class SearchUtils {

    @Resource
    private ArticleService articleService;

    /**
     * 面向对象操作
     * 如定义的名称与配置文件一直则不需要这个
     */
    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

    /**
     * 批量处理文章数据(全量同步) 每天凌晨1点执行一次
     * @throws IOException
     */
    @Scheduled(cron = "0 0 1 * * ?")
    void articleFullUpdate() throws IOException {
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
