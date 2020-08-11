package xyz.fusheng.model.core.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @FileName: Good
 * @Author: code-fusheng
 * @Date: 2020/8/10 21:45
 * @version: 1.0
 * Description: 点赞实体类
 */

@Data
@TableName("m_good")
public class Good {

    /**
     * 点赞id
     */
    @TableId
    private Long goodId;

    /**
     * 点赞用户id (点赞执行者)
     */
    private Long goodUserId;

    /**
     * 点赞对象id
     */
    private Long goodTarget;

    /**
     * 点赞类型
     * 0 - 默认；1 - 文章； 2 - 评论；
     */
    private Integer goodType;

    /**
     * 点赞时间
     */
    private String goodTime;

}
