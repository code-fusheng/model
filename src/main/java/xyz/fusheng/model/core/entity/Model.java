/**
 * @FileName: Model
 * @Author: code-fusheng
 * @Date: 2020/4/14 23:12
 * Description: 模版实体类
 */
package xyz.fusheng.model.core.entity;

import lombok.Data;

/**
 * @author code-fusheng
 * 1. @Data : lombok 插件的声明注解 等价于上面的@Setter、@Getter、@RequiredArgsConstructor、@ToString、@EqualsAndHashCode
 */

@Data
public class Model {

    /**
     * 模版id
     */
    private Integer modelId;

    /**
     * 模版名称
     */
    private String modelName;


}
