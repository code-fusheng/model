package xyz.fusheng.model.core.service;

import org.elasticsearch.action.search.SearchResponse;
import xyz.fusheng.model.common.utils.SearchPage;

import java.io.IOException;

/**
 * @FileName: SearchService
 * @Author: code-fusheng
 * @Date: 2020/5/30 16:25
 * @version: 1.0
 * Description: 搜索业务逻辑接口
 */

public interface SearchService {

    /**
     * 基础高亮分页搜索
     * @param searchPage
     * @return
     * @throws IOException
     */
    SearchResponse searchPageHighlight(SearchPage searchPage) throws IOException;

}
