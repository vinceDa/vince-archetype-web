# COLA 架构脚手架

基于 COLA 5.0 的 Spring Boot 项目脚手架。

## 技术栈

- Spring Boot 3.3.3
- JDK 17
- MyBatis 3.0.3 + MySQL
- MapStruct 1.6.0
- JWT 0.12.6
- Lombok

## 快速开始

### 1. 创建项目
```bash
mvn archetype:generate \
-DgroupId=com.example \
-DartifactId=my-project \
-Dversion=1.0-SNAPSHOT \
-DarchetypeGroupId=com.vince \
-DarchetypeArtifactId=vince-archetype-web \
-DarchetypeVersion=1.0.0 \
-DinteractiveMode=false
```


### 2. 配置数据库
修改 start/src/main/resources/application.properties:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/my-project?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
spring.datasource.username=root
spring.datasource.password=123456
```

### 3. 生成数据库代码

修改 infrastructure/src/main/resources/mybatis/mybatis-generator-config.xml 中的数据库配置:
```xml
<jdbcConnection driverClass="com.mysql.cj.jdbc.Driver"
connectionURL="jdbc:mysql://localhost:3306/your_db"
userId="your_username"
password="your_password">
```

运行 MyBatis Generator:
```bash
cd my-project/my-project-infrastructure
mvn mybatis-generator:generate
```

## 项目结构
my-project
├── my-project-adapter # 适配层: HTTP 请求处理
├── my-project-client # API层: 接口和DTO对象
├── my-project-app # 应用层: 业务流程编排
├── my-project-domain # 领域层: 核心业务逻辑
├── my-project-infrastructure # 基础设施层
└── start # 启动模块


## 主要特性

1. MyBatis 增强插件:
   - Lombok 插件
   - Example 增强插件
   - 分页插件
   - 注释插件

2. 统一异常处理:
   - 业务异常(BizException)
   - 系统异常(SysException)
   - 参数校验异常

3. 日志配置:
   - 基于 logback
   - 按大小和时间滚动
   - 开发和生产环境配置分离

4. 其他特性:
   - JWT 支持
   - MapStruct 对象映射
   - 参数校验
   - 日志切面

## 许可证
MIT License



