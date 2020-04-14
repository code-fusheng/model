/**
 * @FileName: ModelConfig
 * @Author: code-fusheng
 * @Date: 2020/4/14 20:55
 * Description: 模版配置类
 */
package xyz.fusheng.model.common.config;

import org.springframework.context.annotation.Configuration;

/**
 * @author code-fusheng
 * @Configuration : 配置类注解
 * 1. @Component: 将该类注册为 Bean，其内部也可以依赖注入。[换个说法就是可以使用：@Autowired、@Inject、@Scope、、、]
 * 2. @Target({ElementType.TYPE}): 这个枚举类型常量提供了一个简单的分类 注释可能出现在Java程序中的语法位置
 *                                （这些常量与元注释类型(@Target)一起指定在何处写入注释的合法位置）
 * 3. @Retention(RetentionPolicy.RUNTIME): 这个枚举类型的常量保留了注释的各种策略，
 *                                         它们与元注释(@Retention)一起指定注释要保留多长时间
 * 4. @Documented 注解表明这个注释是由 javadoc 记录的，在默认情况下也有类是的记录工具，如果一个类型声明被注释了文档化，它的注释称为公共API的一部分
 *
 *
 * 注解原理 : 通常使用 AnnotationConfigApplicationContext / AnnotationConfigWebApplicationContext 引导
 * AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
 * ctx.register(ModelConfig.class);     // 注册
 * ctx.refresh();
 * ModelBean modelBean = ctx.getBean(ModelConfig.class);
 * 这个过程就像 ClassXmlPathApplicationContent 的工作过程
 */

@Configuration
public class ModelConfig {

}
