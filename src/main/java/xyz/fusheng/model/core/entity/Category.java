package xyz.fusheng.model.core.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.io.Serializable;

/**
 * @FileName: Category
 * @Author: code-fusheng
 * @Date: 2020/5/14 11:53
 * @version: 1.0
 * Description: 分类
 */

@Data
@TableName("m_category")
public class Category implements Serializable {
    private static final long serialVersionUID = -9218163921931220521L;

    /**
     * 分类id 编号
     */
    @TableId
    private String categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 分类图片
     */
    private String categoryImage;

    /**
     * 分类文章数
     */
    private Integer articleCount;

    /**
     * 分类级别
     */
    private Integer level;

    /**
     * 父级分类id
     */
    private Long pid;

    /**
     * 分类创建时间
     */
    private String createdTime;

    /**
     * 分类修改时间
     */
    private String updateTime;

    /**
     * 乐观锁
     */
    @Version
    private Integer version;

    /**
     * 是否启用，0否1是 默认1
     */
    private Integer isEnabled;

    /**
     * 是否删除，0否1是 默认0
     */
    @TableLogic
    private Integer isDeleted;
}
