/**
 * @FileName: ModelMapper
 * @Author: code-fusheng
 * @Date: 2020/4/14 23:18
 * Description: 模版数据访问层
 */
package xyz.fusheng.model.core.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author code-fusheng
 *  1. @Mapper 使用的是 MyBatis 的 Bean
 *  2. @Repository 数据访问层组件,使用的是 Spring 的 Bean 注入，解决 ServiceImpl 注入报错的提示,
 *  3. @Repository 是 @Component 的衍生品
 *  4. @Component 把普通pojo实例化到spring容器中，相当于配置文件中的<bean id="" class=""/>
 */

@Mapper
@Repository
public interface ModelMapper {
}
