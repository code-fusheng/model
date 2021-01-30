/**
 * @FileName: Model
 * @Author: code-fusheng
 * @Date: 2020/4/14 23:12
 * Description: 模版实体类
 */
package xyz.fusheng.model.core.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author code-fusheng
 * 1. @Data : lombok 插件的声明注解 等价于上面的@Setter、@Getter、@RequiredArgsConstructor、@ToString、@EqualsAndHashCode
 * 2. @JsonInclude(JsonInclude.Include.NON_NULL) : 实体类与json互转的时候 属性值为null的不参与序列化， 可在 application.yml 里面全局配置
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Model implements Serializable {
    /**
     * 1. File -> setting -> plugins  安装 GenerateSerialVersionUID 插件
     * 2. Win 光标 Serializable Alt+Insert 生成序列化唯一标识
     * 3. Mac Serializable command + N
     */
    private static final long serialVersionUID = 8400330887851994225L;

    /**
     * 模版id
     */
    private Integer modelId;

    /**
     * 模版名称
     */
    private String modelName;

    /**
     * 模版创建时间
     */
    private String createdTime;

    /**
     * 模版修改时间
     */
    private String updateTime;

    /**
     * 乐观锁
     */
    private Integer version;

    /**
     * 是否启用，0否1是 默认1
     */
    private Integer isEnabled;

    /**
     * 是否删除，0否1是 默认0
     */
    private Integer isDeleted;


}
