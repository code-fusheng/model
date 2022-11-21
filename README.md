# model
【code-fusheng 个人后端模版】

### 开发记录
```yaml
(2020/4/14 20:43 --- 2020/4/14 23:23) code-fusheng 后端模版 --- 创建了项目，并配置了相关的依赖文件
(2020/4/15 12:25 --- 2020/4/15 15:22) code-fusheng 后端模版 --- 添加了启动类实现的相关说明，规范了项目的结构层
(2020/4/16 10:21 --- 2020/4/16 11:58) code-fusheng 后端模版 --- 添加了 aop + logback 日志的相关内容，分页，以及统一返回结果
(2020/4/26 10:34 --- 2020/4/27 01:17) code-fusheng 后端模版 --- 添加了用户-角色-权限关系（待完善）Security + JWT
(2020/4/27 15:34 --- 2020/4/27 16:32) code-fusheng 后端模版 --- 完善了权限设计，基本实现了权限控制
(2020/4/29 10:21 --- 2020/4/29 22:10) code-fusheng 后端模版 --- 添加了swagger接口文档配置，完善了modelPlus，重构了表结构
(2020/5/04 10:13 --- 2020/5/04 21:28) code-fusheng 后端模版 --- 添加了fastdfs分布式文件服务的相关配置以及项目多环境配置
(2020/5/05 10:41 --- 2020/5/05 16:26) code-fusheng 后端模版 --- 修改了用户的相关接口，提供了用户的信息，权限，角色查询
(2020/5/05 19:13 --- 2020/5/06 00:41) code-fusheng 后台模版 --- 创建了基础项目框架
(2020/5/06 08:32 --- 2020/5/06 16:12) code-fusheng 后端模版 --- 增强模版添加了图片属性
(2020/5/06 08:32 --- 2020/5/06 16:12) code-fusheng 后台开发 --- 添加了模版模块，实现了基本的相关照做，新增图片上传
(2020/5/07 08:12 --- 2020/5/08 00:24) code-fusheng 项目部署 --- 配置了nginx相关部署更改security异常符号请求配置
(2020/5/07 08:12 --- 2020/5/08 00:24) code-fusheng 后台开发 --- 修改了后台动态代理相关的配置
(2020/5/08 10:47 --- 2020/5/08 18:54) code-fusheng 后端模版 --- 添加了以POI为基础的excel表格导入导出工具,实现了后台的日志导出
(2020/5/11 15:57 --- 2020/5/11 17:33) code-fusheng 后端模版 --- 添加了Redis缓存的配置，实现了Mybatis-Plus的二级缓存
(2020/5/13 11:34 --- 2020/5/13 23:39) code-fusheng 后端模版 --- 稍微完善了用户角色权限的相关功能
(2020/5/14 12:31 --- 2020/5/14 11:55) code-fusheng 后端模版 --- 添加了文章和分类的实体以及查询操作，添加了Elasticsearch依赖
(2020/5/15 00:12 --- 2020/5/15 01:19) code-fusheng 文章模块 --- 运用MyBatisPlus的条件构造器优化了文章模块的部分功能
(2020/5/15 10:27 --- 2020/5/15 16:04) code-fusheng 后端模版 --- 编写了 Elasticsearch 相关操作的测试类，实现CRUD以及目标字段高亮显示
(2020/5/30 14:10 --- 2020/5/30 14:11) code-fusheng 文章模块 --- 补充提交浅一短时间添加的文章分类内容
(2020/5/30 14:14 --- 2020/5/30 15:07) code-fusheng 后端模版 --- 优化高级模版的MyBaitsPlus语法
(2020/5/30 15:07 --- 2020/5/30 17:10) code-fusheng 文章模块 --- 优化了文章模块的相关逻辑，修复了乐观锁（@Version）与逻辑删除（@TableLogic）问题，添加了简单搜索的功能
(2020/5/31 02:21 --- 2020/5/31 04:38) code-fusheng 用户模块 --- 完善了用户信息的查询，修复了前后端用户信息交互问题 admin/info（前后台）
(2020/5/31 02:21 --- 2020/5/31 04:38) code-fusheng 文章模块 --- 完善了文章模块的相关接口，关于数据CRUD后的正确性有待加强，添加了Elasticsearch定时同步功能，初步封装了搜索接口
(2020/6/02 06:25 --- 2020/6/02 16:21) code-fusheng 文章模块 --- 完善了文章模块中文章和分类之间的逻辑，保证了数据的正确性（同期实现了对应的后台页面）
(2020/6/08 09:21 --- 2020/6/08 11:38) code-fusheng 搜索模块 --- 完善了Elasticsearch相关的方法 添加了搜索模块的测试类、实现了部分常用的方法（文章的CRUD、基础高亮、多字段高亮搜索）
(2020/7/18 07:45 --- 2020/7/18 13:01) code-fusheng 系统整体 --- 调整相关的字段，优化前后端，完善逻辑功能设计
(2020/7/18 13:14 --- 2020/7/18 16:33) code-fusheng 权限设计 --- 初步实现了格式化的权限列表查询方法（存在小BUG:递归问题 - 未解决）
(2020/8/09 20:12 --- 2020/8/09 22:42) code-fusheng 评论模块 --- 初步设计了评论的功能接口
(2020/8/10 21:38 --- 2020/8/10 22:47) code-fusheng 文章模块 --- 初步设计点赞、收藏实体类以及层次结构
(2020/8/11 08:30 --- 2020/8/11 17:04) code-fusheng 评论模块 --- 进一步完善评论模块的设计与前后端交互的实现（目前多级评论接口正常）
(2020/8/13 08:37 --- 2020/8/13 22:05) code-fusheng 点赞收藏 --- 初步完成了文章模块的点赞、收藏操作的后端接口 good 、collection
(2020/8/14 12:00 --- 2020/8/14 18:28) code-fusheng 评论模块 --- 多级评论的功能设计（总觉的很垃圾）
更新开发记录规范 --- Git Commit 个人开发规范
(2020/08/15 23:00 --- 2020/08/15 23:08) docs : 文档管理 --- 新增开发规范的 git commit 的规范要求与说明
(2020/08/16 11:19 --- 2020/08/16 14:38) feature : 音乐欣赏 --- 新增音乐相关的后端接口（论坛中仅涉及简单的音乐模块，后续单独写音乐播放器以及视频播放器）
(2020/08/20 06:41 --- 2020/08/20 20:11) feature : 文章模块 --- 实现了文本内容的编辑修改，增加 editContent 编辑内容字段 保存文章内容源码
(2020/08/26 09:35 --- 2020/08/26 13:49) fix : 登录逻辑 --- 解决了 Security 未登录状态资源访问问题，具体见 LoginController 的 callbackLogin() 方法
(2020/08/27 09:42 --- 2020/08/27 13:17) feature : 权限设计 --- 通过改变字段，实现了后台添加权限的功能，在常规模版上添加而注解以及路由标识控制权限（下一步，后台角色全向绑定操作）
(2020/09/05 20:00 --- 2020/09/05 23:13) feature : 登录日志 --- 新增日志管理-登录日志的相关接口方法(UserAgentUtils 浏览器解析客户端操作系统、浏览器、淘宝IP地址查询接口),登录操作保存登录信息
(2020/09/06 10:20 --- 2020/09/06 14:07) feature : 登录日志、公共操作 --- 完善了登录日志相关的内容，解决了部分公共操作模块的分页查询BUG
(2020/09/12 08:00 --- 2020/09/12 23:12) feature : 权限设计 --- 新增权限树形设计、新增权限方法优化、权限菜单内容优化（基本完工）
(2020/09/12 08:00 --- 2020/09/13 17:09) feature : 权限设计 --- 新增树形权限设计，提供用户授权方法，优化角色权限中间表 sys_role_menu 设计，基本实现角色与权限的设计
(2020/09/13 10:00 --- 2020/09/14 00:47) feature : 权限设计 --- 完善了角色与用户的相关功能，完善了用户实体，优化了相关逻辑设计，（剩余用户与角色的绑定没有完成）
(2020/09/14 14:00 --- 2020/09/14 16:11) feature : 权限设计 --- 新增了用户与角色的绑定功能，权限设计基本完成，只剩权限表内容的完善与后端 Security 安全框架的注解完善
(2020/09/14 20:00 --- 2020/09/15 01:00) feature : 权限设计 --- 完成用户-角色-权限 RBAC 设计，同时结合 Security 注解完成权限控制 —— 阶段性 V1.0.1 版本发布
(2020/09/21 23:00 --- 2020/09/22 00:51) feature : 消息通知 --- 新增消息实体对象，增加、删除、更新、确认等操作接口，优化分层架构设计，ServiceImpl与Service层不再实现MybatisPlus接口，而在Service层中做接口统一处理
(2020/09/22 09:20 --- 2020/09/22 15:32) feature、fix : 消息通知、登录日志 --- 新增消息通知前后端交互接口，修复登录日志分页查询异常问题，<where>动态SQL位置问题
(2020/09/22 18:45 --- 2020/09/22 22:37) feature : 消息通知 --- 完善了消息通知模块内容，新增点赞、收藏消息通知（PS:二级评论设计存在缺陷，导致通知数据存在一定问题）
(2020/09/23 18:00 --- 2020/09/24 01:10) feature : 接口文档 --- 新增高级模版 swagger 接口注释说明、优化了 swagger 接口展示界面的风格（存在Security登录接口无法导入、全局token参数配置问题）
(2020/09/29 08:00 --- 2020/09/29 09:54) fix : 接口文档 --- 解决了iframe无法嵌入页面的问题, SecurityConfig 配置 X-Frame-Options 问题
(2020/10/05 12:00 --- 2020/10/05 12:41) fix : 文件存储 --- 调整 fastDFS 端口配置（80 -> 8888）解决前端无法显示图片的冲突
(2020/10/06 10:00 --- 2020/10/06 11:43) feature : 操作日志 --- 新增操作日志 operaLog 的相关接口，新增相关的切面（自定义注解）
(2020/10/07 23:10 --- 2020/10/08 10:00) feature : 操作日志 --- 接口添加操作日志注解说明
(2020/10/31 07:00 --- 2020/10/31 12:51) feature : 短信登录 --- 新增短信登录认证接口、阿里云短信服务+Security(异常链问题)
(2020/12/09 09:30 --- 2020/12/09 11:32) feature : 文件上传 --- 新增阿里云 OSS 对象存储服务，替换 FastDFS 图片服务器为阿里云OSS
(2021/01/05 10:20 --- 2021/01/05 12:30) version : 版本发布
(2021/01/08 22:00 --- 2021/01/09 12:25) feature : 登陆日志 --- 新增调用腾讯IP定位接口，丰富系统Log日志打印
(2021/01/27 09:00 --- 2021/01/27 18:24) feature : 消息队列 --- 新增消息中间件 - RabbitMQ 测试Demo,采用RabbitMQ优化登陆日志异步处理
(2021/01/30 10:37 --- 2021/01/30 10:40) fix : 系统配置 --- 新增阿里云子账号
(2021/02/10 10:00 --- 2021/02/11 22:45) feature : 文章阅读 --- 新增文章阅读 上一篇与下一篇 接口
(2021/03/01 09:00 --- 2021/03/01 10:00) fix : 短信登陆 --- 修复短信登陆Token颁发时无效的问题
(2021/03/02 09:00 --- 2021/03/02 10:00) feature : 第三方登陆 --- 新增Github第三方登陆

(2022/02/01 00:00 --- 2022/02/05 00:00) feature : 自动部署 --- 新增 jenkins + Github 自动化部署 TT
(2022/11/21 09:00 --- 2022/11/22 00:58) feature : 系统配置 --- 新增关于我的系统配置接口
```
