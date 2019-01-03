# spring-boot-hashids

#### 介绍

在spring-boot中将整型的id转换为字符串的id，或者字符串的id转换为整型的id
spring-boot-hashids作为一个springboot的一个插件使用，目前兼容spring-boot 2.x

#### 使用说明

1. 启动Application继承WebApplication
2. 在vo对象的字段中使用@HashidsFormat注解

#### 使用示例

1. 打开MemberController.java查看路由路径
2. 请求会员列表接口 [http://localhost:8080/members](http://localhost:8080/members)
3. 请求结果展示：![请求结果展示](https://github.com/284885166/spring-boot-hashids/blob/master/screenshot/res.png?raw=true)

#### 参与贡献

1. Fork 本仓库
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request
