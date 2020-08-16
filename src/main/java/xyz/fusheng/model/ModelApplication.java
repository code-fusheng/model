/**
 * 一、[@SpringBootApplication] : 是一个复合注解，包括 @ComponentScan、@SpringBootConfiguration、@EnableAutoConfiguration
 * 1. @Target(ElementType.TYPE): 这个枚举类型常量提供了一个简单的分类 注释可能出现在Java程序中的语法位置
 *                             （这些常量与元注释类型(@Target)一起指定在何处写入注释的合法位置）
 * 2. @Retention(RetentionPolicy.RUNTIME): 这个枚举类型的常量保留了注释的各种策略，
 *                                        它们与元注释(@Retention)一起指定注释要保留多长时间
 * 3. @Documented 注解表明这个注释是由 javadoc 记录的，在默认情况下也有类是的记录工具，如果一个类型声明被注释了文档化，它的注释称为公共API的一部分
 * 4. @ComponentScan: 扫描当前包以及其子包下被 @Component、@Controller、@Service、@Repository 注解标记的类并纳入 Spring 容器中进行管理。
 *                    => <context:component-scan> 等价于xml使用标签配置
 * 5. @SpringBootConfiguration: 继承 @Configuration, 二者功能一致，标注当前类是配置类，
 *                             并且将当前类内部声明的一个或者多个以 @Bean 标注的方法实例纳入到 Spring 容器中，并且实例名就是方法名
 * 6. EnableAutoConfiguration: 作用启动自动配置，注解的意思是说 spring boot 根据添加的jar包来配置项目的默认配置，
 *                            比如根据 spring-boot-starter-web 来判断项目是否需要webmvc/tomcat
 *
 * 二、[@EnableTransactionManagement] : 开启事务支持注解，然后可以在 Service 业务逻辑的方法上添加注解 @Transactional
 * 事务管理器: 不管是 JPA 还是 JDBC 都是事先子接口 PlatformTransactionManager
 * 1. spring-boot-starter-jdbc 依赖，默认注入 DataSourceTransactionManager 实例
 * 2. spring-boot-starter-data-jpa 依赖，默认注入 JpaTransactionManager 实例
 * 3、@Import: 导入第三方资源（普通的java类） 以 Bean 的形式注入到 Spring 容器
 *
 * 三、[@MapperScan] : mapper 包扫描 注解原理 --> @MapperScan 是根据其注解上的 @Import({MapperScannerRegistrar.class}) 进行自定配置的
 * 注解思路: 首先根据标注的 @MapperScan 获取 basePackage 或者 @Mapper 或者所在的 package,之后通过 ClassPathMapperScanner 去扫描包，
 *          获取所有 Mapper 接口类的 BeanDefinition，之后具体配置，设置 beanClass 为 MapperFactoryBean,设置 MapperFactoryBean 的构造器
 *          参数为实际的 Mapper 接口类，通过 ClassPathBeanDefinitionScanner 父类进行 bean 注册，自动注入的时候，就会调用 MapperFactoryBean
 *          的 getObject 方法获取实际类型的实例
 * 简而言之: 1.@Mapper --> 把 mapper 接口类注册成 spring 的 bean
 *          2.@MapperScan --> 扫描包下的接口，批量注册成 bean
 *          作用一致，写一个就行
 *
 * 1、Spring Boot 源码解析 : 关键点 spring 与 spring boot 技术衔接点 refresh
 * 2、重点 ？ 准备工作 自动装配 tomcat的内嵌 监听器listener 整个启动流程
 */
package xyz.fusheng.model;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author code-fusheng
 * @Data: 2020/4/14 20:43 --- 2020/4/14 23:23 code-fusheng 后端模版 --- 创建了项目，并配置了相关的依赖文件
 * @Data: 2020/4/15 12:25 --- 2020/4/15 15:22 code-fusheng 后端模版 --- 添加了启动类实现的相关说明，规范了项目的结构层
 * @Data: 2020/4/16 10:21 --- 2020/4/16 11:58 code-fusheng 后端模版 --- 添加了 aop + logback 日志的相关内容，分页，以及统一返回结果
 * @Data: 2020/4/26 10:34 --- 2020/4/27 01:17 code-fusheng 后端模版 --- 添加了用户-角色-权限关系（待完善）Security + JWT
 * @Data: 2020/4/27 15:34 --- 2020/4/27 16:32 code-fusheng 后端模版 --- 完善了权限设计，基本实现了权限控制
 * @Data: 2020/4/29 10:21 --- 2020/4/29 22:10 code-fusheng 后端模版 --- 添加了swagger接口文档配置，完善了modelPlus，重构了表结构
 * @Data: 2020/5/04 10:13 --- 2020/5/04 21:28 code-fusheng 后端模版 --- 添加了fastdfs分布式文件服务的相关配置以及项目多环境配置
 * @Data: 2020/5/05 10:41 --- 2020/5/05 16:26 code-fusheng 后端模版 --- 修改了用户的相关接口，提供了用户的信息，权限，角色查询
 * @Data: 2020/5/05 19:13 --- 2020/5/06 00:41 code-fusheng 后台模版 --- 创建了基础项目框架
 * @Data: 2020/5/06 08:32 --- 2020/5/06 16:12 code-fusheng 后端模版 --- 增强模版添加了图片属性
 * @Data: 2020/5/06 08:32 --- 2020/5/06 16:12 code-fusheng 后台开发 --- 添加了模版模块，实现了基本的相关照做，新增图片上传
 * @Data: 2020/5/07 08:12 --- 2020/5/08 00:24 code-fusheng 项目部署 --- 配置了nginx相关部署更改security异常符号请求配置
 * @Data: 2020/5/07 08:12 --- 2020/5/08 00:24 code-fusheng 后台开发 --- 修改了后台动态代理相关的配置
 * @Data: 2020/5/08 10:47 --- 2020/5/08 18:54 code-fusheng 后端模版 --- 添加了以POI为基础的excel表格导入导出工具,实现了后台的日志导出
 * @Data: 2020/5/11 15:57 --- 2020/5/11 17:33 code-fusheng 后端模版 --- 添加了Redis缓存的配置，实现了Mybatis-Plus的二级缓存
 * @Data: 2020/5/13 11:34 --- 2020/5/13 23:39 code-fusheng 后端模版 --- 稍微完善了用户角色权限的相关功能
 * @Data: 2020/5/14 12:31 --- 2020/5/14 11:55 code-fusheng 后端模版 --- 添加了文章和分类的实体以及查询操作，添加了Elasticsearch依赖
 * @Data: 2020/5/15 00:12 --- 2020/5/15 01:19 code-fusheng 文章模块 --- 运用MyBatisPlus的条件构造器优化了文章模块的部分功能
 * @Data: 2020/5/15 10:27 --- 2020/5/15 16:04 code-fusheng 后端模版 --- 编写了 Elasticsearch 相关操作的测试类，实现CRUD以及目标字段高亮显示
 * @Data: 2020/5/30 14:10 --- 2020/5/30 14:11 code-fusheng 文章模块 --- 补充提交浅一短时间添加的文章分类内容
 * @Data: 2020/5/30 14:14 --- 2020/5/30 15:07 code-fusheng 后端模版 --- 优化高级模版的MyBaitsPlus语法
 * @Data: 2020/5/30 15:07 --- 2020/5/30 17:10 code-fusheng 文章模块 --- 优化了文章模块的相关逻辑，修复了乐观锁（@Version）与逻辑删除（@TableLogic）问题，添加了简单搜索的功能
 * @Data: 2020/5/31 02:21 --- 2020/5/31 04:38 code-fusheng 用户模块 --- 完善了用户信息的查询，修复了前后端用户信息交互问题 admin/info（前后台）
 * @Data: 2020/5/31 02:21 --- 2020/5/31 04:38 code-fusheng 文章模块 --- 完善了文章模块的相关接口，关于数据CRUD后的正确性有待加强，添加了Elasticsearch定时同步功能，初步封装了搜索接口
 * @Data: 2020/6/02 06:25 --- 2020/6/02 16:21 code-fusheng 文章模块 --- 完善了文章模块中文章和分类之间的逻辑，保证了数据的正确性（同期实现了对应的后台页面）
 * @Data: 2020/6/08 09:21 --- 2020/6/08 11:38 code-fusheng 搜索模块 --- 完善了Elasticsearch相关的方法 添加了搜索模块的测试类、实现了部分常用的方法（文章的CRUD、基础高亮、多字段高亮搜索）
 * @Data: 2020/7/18 07:45 --- 2020/7/18 13:01 code-fusheng 系统整体 --- 调整相关的字段，优化前后端，完善逻辑功能设计
 * @Data: 2020/7/18 13:14 --- 2020/7/18 16:33 code-fusheng 权限设计 --- 初步实现了格式化的权限列表查询方法（存在小BUG:递归问题 - 未解决）
 * @Data: 2020/8/09 20:12 --- 2020/8/09 22:42 code-fusheng 评论模块 --- 初步设计了评论的功能接口
 * @Data: 2020/8/10 21:38 --- 2020/8/10 22:47 code-fusheng 文章模块 --- 初步设计点赞、收藏实体类以及层次结构
 * @Data: 2020/8/11 08:30 --- 2020/8/11 17:04 code-fusheng 评论模块 --- 进一步完善评论模块的设计与前后端交互的实现（目前多级评论接口正常）
 * @Data: 2020/8/13 08:37 --- 2020/8/13 22:05 code-fusheng 点赞收藏 --- 初步完成了文章模块的点赞、收藏操作的后端接口 good 、collection
 * @Data: 2020/8/14 12:00 --- 2020/8/14 18:28 code-fusheng 评论模块 --- 多级评论的功能设计（总觉的很垃圾）
 * 更新开发记录规范 --- Git Commit 个人开发规范
 * (2020/08/15 23:00 --- 2020/08/15 23:08) docs : 文档管理 --- 新增开发规范的 git commit 的规范要求与说明
 * (2020/08/16 11:19 --- 2020/08/16 14:38) feature : 音乐欣赏 --- 新增音乐相关的后端接口（论坛中仅涉及简单的音乐模块，后续单独写音乐播放器以及视频播放器）
 *
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("xyz.fusheng.model.core.mapper")
@EnableCaching
@EnableScheduling
public class ModelApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModelApplication.class, args);
    }

}
