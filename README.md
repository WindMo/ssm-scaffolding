# SSM脚手架 - 纯Java类配置

虽然现在都是SpringBoot，但是总有傻宝公司或傻宝项目各种限制，这个时候搞一个纯Java类配置的ssm脚手架就很有必要了，不用维护繁琐的bean xml，接近SpringBoot的写法和配置。

- 过滤器配置使用DelegatingFilterProxy代理，过滤器加入IOC即可使用
- 集成lombok + logback
- 附单元测试配置

PS：单元测试配置的不足：由于过滤器配置在`WebApplicationInitializer`接口（Java类配置的关键接口）的实现类中，目前没找到使用该接口进行初始化操作进行单元测试的方法（反正没找着文档，源码里也没见着），所以http接口的单元测试中过滤器是不生效的

class

```
ws.ssm.scaffolding
|-annoation  #注解扩展
|-config  #SSM主要配置
| |-dao
| | |-datasource  #数据源配置
| | |-mybatis  #mybatis配置
| |-swagger  #swagger配置
| |-web  #mvc配置
| |-example #demo示例
| |-initializer  #servlet初始化相关
```

resources

```
resources
|-db  #demo示例sql脚本
|-mappers mapper的xml配置
|-ApplicationProperties.properties  #全局配置文件入口
|-logback.xml  #logback日志配置
```