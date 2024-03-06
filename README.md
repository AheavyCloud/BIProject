# AI-enhanced BI Project
## First Commit
  ### 1. 项目进度
   1. 完成登录、注册请求开发。
   2. 完成对MySQL数据库CRUD基本操作的开发。
   3. 使用全局异常处理类，收集后端登录以及注册时可能产生的异常，并通过自定义的错误信息类，将错误信息返回给前端。
 ### 2. 第一阶段技术选型
   使用Springboot作为web开发的基本框架，使用MySQL数据库存储用户信息，使用Mybatis-plus实现对数据表的CRUD操作。
## Second Commit
 ### 1. 项目进度
   1. 完成调用AI接口处理用户请求，生成数据
   2. 完成利用Redisson类调用Redis实现用户请求限流
 ### 2. 技术选型
   1. 使用Redis实现用户限流
## Third Commit
 ### 1. 项目进度
   1. 使用ThreadPoolExecutor线程池技术，完成对用户的异步化操作

### 2. 技术选型
   1. 线程池ThreadPoolExecutor
## 下阶段目标：
### RabbitMQ
    使用消息队列完成对AI请求的调用，实现后端代码服务器与AI接口的解耦。
