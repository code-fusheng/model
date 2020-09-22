package xyz.fusheng.model.core.vo;

import lombok.Data;
import xyz.fusheng.model.core.entity.Good;

/**
 * @FileName: GoodVo
 * @Author: code-fusheng
 * @Date: 2020/8/13 15:30
 * @version: 1.0
 * Description: 点赞视图实体
 */

@Data
public class GoodVo extends Good {

    /**
     * 点赞人用户名
     */
    private String username;

    /**
     * 点赞对象描述
     */
    private String targetDesc;

    /**
     * 点赞对象用户名
     */
    private String targetUserName;
}
