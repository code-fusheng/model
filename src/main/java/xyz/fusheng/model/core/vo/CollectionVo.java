package xyz.fusheng.model.core.vo;

import lombok.Data;
import xyz.fusheng.model.core.entity.Collection;

/**
 * @FileName: CollectionVo
 * @Author: code-fusheng
 * @Date: 2020/8/13 19:37
 * @version: 1.0
 * Description: 收藏视图实体类
 */
@Data
public class CollectionVo extends Collection {

    /**
     * 收藏人用户名
     */
    private String username;

    /**
     * 收藏对象描述
     */
    private String targetDesc;

}
