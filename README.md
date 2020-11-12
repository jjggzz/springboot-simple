# springboot-simple
## 项目启动
@SpringBootApplication(scanBasePackages = "com.springboot.simple")
## jdbc动态数据源
1)导包
````
<dependency>
    <groupId>com.jgz</groupId>
    <artifactId>springboot-simple-jdbc</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
````
2)yml文件数据源配置
````
master:
  driver-class-name: org.gjt.mm.mysql.Driver
  url: jdbc:mysql://127.0.0.1:3306/demo
  username: root
  password: 123456
  #附加配置，不配则使用默认值
  min-idle: 10
  initial-size: 10
  max-active: 10
  max-wait-millis: 400
  time-between-eviction-runs-millis: 60000
  min-evictable-idle-time-millis: 300000
  validation-query: SELECT 1 FROM DUAL
  test-while-idle: true
  test-on-borrow: false
  test-on-return: false
  pool-prepared-statements: true
  max-pool-prepared-statement-per-connection-size: 20
  filters: stat,wall
  use-global-data-source-stat: true
  connection-properties:
    mergeSql: true
    slowSqlMillis: 500

slave:
  driver-class-name: org.gjt.mm.mysql.Driver
  url: jdbc:mysql://127.0.0.1:3306/demo1
  username: root
  password: 123456
  #附加配置，不配则使用默认值
  min-idle: 5
  initial-size: 5
  max-active: 10
  max-wait-millis: 200
  time-between-eviction-runs-millis: 60000
  min-evictable-idle-time-millis: 300000
  validation-query: SELECT 1 FROM DUAL
  test-while-idle: true
  test-on-borrow: false
  test-on-return: false
  pool-prepared-statements: true
  max-pool-prepared-statement-per-connection-size: 20
  filters: stat,wall,log4j
  use-global-data-source-stat: true
  connection-properties:
    mergeSql: true
    slowSqlMillis: 500
````
3)使用注解切换数据源，类和方法上都可以标注，方法的优先级高
````
//切换至主库（默认）
@DataSource(DynamicDataSource.MASTER)
//切换至从库
@DataSource(DynamicDataSource.SLAVE)
````
## 整合redis
1)导包
````
<dependency>
    <groupId>com.jgz</groupId>
    <artifactId>springboot-simple-redis</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
````
2)yml文件redis配置
````
spring:
  redis:
    host: localhost
    port: 6379
````
3)在操作redis的地方注入bean
````
//redis工具类
@Autowired
private RedisUtils redisUtils;
````
## mvc
1）导包
````
<dependency>
    <groupId>com.jgz</groupId>
    <artifactId>springboot-simple-mvc</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
````
2)使用方式
````
    @GetMapping("user/{id}")
    public ResultEntity<UserModel> get(@PathVariable("id") Long id){
        return ResultEntity.success(userService.getById(id));
    }
````
  返回数据
````
{
    "code":1,
    "message":"操作成功",
    "data":{
        "id":1,
        "createTime":"2020-03-29T01:24:57.000+0000",
        "modifiedTime":"2020-03-29T01:24:57.000+0000",
        "deleted":false,
        "name":"zhangs",
        "age":18,
        "sex":"男"
        }
}
````
3)业务异常处理,只需抛出GlobalException即可
````
    if(id == 100){
        throw new GlobalException("信息不存在");
    }
````
  返回数据
````
{
    "status":-1,
    "message":"信息不存在",
    "data":null
}
````
