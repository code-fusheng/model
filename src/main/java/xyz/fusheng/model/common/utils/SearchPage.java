package xyz.fusheng.model.common.utils;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @FileName: SearchPage
 * @Author: code-fusheng
 * @Date: 2020/5/18 9:25
 * @version: 1.0
 * Description:
 */

@Data
public class SearchPage<T> implements Serializable {

    private static final long serialVersionUID = -5177498529969801742L;
    /**
     * 输入关键字 eg:测试
     */
    private String keyword;

    /**
     * 全文检索的字段
     */
    private String[] keyFields;

    /**
     * 当前页号 eg:1  注意 这里是起始页号
     */
    private Integer pageNo;
    /**
     * 分页大小 eg:10
     */
    private Integer pageSize;
    /**
     * 目前 eg:articleTitle
     */
    private String params;
    /**
     * 索引 eg:model_article_index
     */
    private String index;

    /**
     * 数据
     */
    private List<T> list;

    /**
     * 对象实体
     */
    private T searchEntity;
}
