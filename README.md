
# 1.background
  我太难了!!之前在公司上班,受老大哥启发[@likowong](https://github.com/likowong),再加上自己也厌烦了项目中的一大堆配置.虽然springboot已经够简单了,但是也免不了要写点自定义的配置类,就想着能不能把这些重复的东西抽出来,因为配置都大同小异.比如说动态数据源\\全局异常处理\\mysql主从动态数据源\\异步事件调用抽取等等,所以才有了这个东东.
# 2.structure
  ````
  springboot-simple
  ├── springboot-simple-base -- 包含异步封装
  ├── springboot-simple-demo -- 一些示例
  ├── springboot-simple-jdbc -- 数据库相关的封装
  ├── springboot-simple-redis -- 包含redis相关的配置及工具(单节点)
  ├── springboot-simple-mvc -- web相关的简化,全局异常,公共返回体等等
  ├── springboot-simple-aliyun-oss -- aliyun oss相关的封装(研究中)
  └── springboot-simple-support -- 公共常量(避免魔法值),一些工具类
  ````
# 3.Install
  安装方式:\
  github上下载源码.\
  使用maven的install打包.\
  使用gav坐标引入模块.
# 4.Usage
## 项目启动
  注意:组件都在com.springboot.simple包下,启动时请扫描该包
````
  @SpringBootApplication(scanBasePackages = "com.springboot.simple")
````
## jdbc动态数据源
  该模块支持一主多从的数据源配置(目前只支持mysql),通过类或者方法上的注解可以实现运行时的数据源切换,若配置有多个从库则在使用注解选择从库时会随机选择一个从库,若未配置从库则slave模式不生效(只会操作master).使用方式如下.
### 一.导包
````
<dependency>
    <groupId>com.jgz</groupId>
    <artifactId>springboot-simple-jdbc</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
````
### 二.yml文件数据源配置
````
jdbc:
  master:
    driver-class-name: org.gjt.mm.mysql.Driver
    url: jdbc:mysql://127.0.0.1:3306/demo
    username: root
    password: 123456
    #附加配置，不配则使用默认值,slave同理
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
  slave-list:
    - driver-class-name: org.gjt.mm.mysql.Driver
      url: jdbc:mysql://127.0.0.1:3306/demo1
      username: root
      password: 123456
    - driver-class-name: org.gjt.mm.mysql.Driver
      url: jdbc:mysql://127.0.0.1:3306/demo2
      username: root
      password: 123456
````
### 三.使用注解切换数据源，类和方法上都可以标注，方法的优先级高
````
//切换至主库（默认）
@DataSource(DynamicDataSource.MASTER)
//切换至从库(会从已有配置的从库中随机选择一个)
@DataSource(DynamicDataSource.SLAVE)
````
## mybatis-generator的baseDao实现
  这是一套BaseDao的抽象.我们对于每张表都有可能涉及到最基本的增删改查等操作,使用它可以减少我们的重复工作.在使用之前我们需要一些前置条件,这些条件是强制的(暂时不支持自定义,比较菜,逆向工程的源码没时间撸).
  1) 数据表中必须包含如下字段
  ````
  id bigint auto_increment,
  create_time datetime,
  modified_time datetime,
  deleted boolean default 0,
  primary key(id)
  ````
  2) 逆向工程生成代码时,这些字段不能忽略\
  3) 启动了column\\field之间的驼峰转换
### 一.导包
````
<dependency>
    <groupId>com.jgz</groupId>
    <artifactId>springboot-simple-jdbc</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
````
### 二.实体类\\Examlpe\\Mapper接口
````
//实体类
public class UserModel extends BaseModel {

//Example
public class UserModelExample extends BaseModelExample {

//Mapper
public interface UserModelMapper extends BaseModelMapper<UserModel,UserModelExample> {
````
### 三.service接口及serviceImpl实现类
````
//service接口
public interface UserService extends BaseService<UserModel, UserModelExample> {

//serviceImpl实现类
public class UserServiceImpl extends BaseServiceImpl<UserModel, UserModelMapper, UserModelExample> implements UserService {
  @Autowired
  @Override
  public void setMapper(UserModelMapper mapper) {
      super.setMapper(mapper);
  }
}
````
### 四.使用
此时,你可以在业务层中直接调用这些基础的方法.
````
public interface BaseService<T,E> {

    int insert(T model);

    int delete(Long id);

    int update(T model);

    T selectByPrimaryKey(Long id);

    int count(E example);
}
````
## mvc全局异常处理\\返回值\\请求体响应体获取
  1)全局异常处理,若系统出现运行中异常,并不会将异常返回给用户,而是返回给用户一个友好的提示(系统正在忙),出现这个代表你的程序出现的碧油鸡!!赶快排查.\
  2)可以抛出一些业务异常,比如参数异常啦,数据错误啦什么的,用GlobalException包裹,处理器会将其返回给用户.\
  3)使用统一的返回结果集,有默认状态码,也可自定义返回状态码,实现BaseResultEnum接口即可.\
  4)通过继承BaseController简化request和response的获取(可以不需要将其显式的放置在方法参数上,这样不太好看!!)
### 一.导包
````
<dependency>
    <groupId>com.jgz</groupId>
    <artifactId>springboot-simple-mvc</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
````
### 二.全局异常与业务异常处理
````
@Override
public UserModel getById(Long id) {
    //抛出异常,返回给前端的是系统忙
    int i = 1/0;
    if(id == 100){
        //自定义抛出业务异常
        throw new BusinessException(1000,"信息不存在");
    }
    return selectByPrimaryKey(id);
}
````
### 三.统一格式数据返回
还有一些其他使用方法,很简单,可以点进ResultEntity里面看看
````
@GetMapping("user/{id}")
public ResultEntity<UserModel> get(@PathVariable("id") Long id){
    return ResultEntity.success(userService.getById(id));
}
````
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
### 四.自定义返回状态码
实现BaseResultEnum接口
````
public class DefaultResult implements BaseResultEnum {

        private Integer code;

        private String message;

        public DefaultResult(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public Integer getCode() {
            return code;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }
````
### 五.在controller方法中获取请求体
使controller继承BaseController,即可调用getRequest()方法和getResponse()方法
````
@RestController
public class UserController extends BaseController {

    @PostMapping("/user")
    public void add(@RequestBody User user){
        HttpServletRequest request = getRequest();
        //......剩下的业务操作
    }
}
````
## 异步调用抽取
 有时需要异步执行一些逻辑,但是需要编写事件Event类和处理逻辑等等,简单重复的工作都是垃圾工作,希望把心思聚焦在逻辑实现上,故将这些东西做了抽取
### 一.导包
````
<dependency>
    <groupId>com.jgz</groupId>
    <artifactId>springboot-simple-base</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
````
### 二.实现接口
实现BaseHandlerInterface接口,重写handler方法,在这里面实现相应的逻辑
````
public class DemoHandler implements BaseHandlerInterface {

    private String name;

    private Integer age;

    public DemoHandler(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    //比如这里异步打印了一些数据...
    @Override
    public void handler() {
        System.out.println(new Date() + ":" + name + ":" + age);
    }
}
````
### 三.事件发布执行
注入对象,发布事件
````
//注入发布者对象
@Autowired
private EventPusher eventPusher;

//事件发布
eventPusher.eventPush(new BaseEvent(new DemoHandler("张三",18)));
````

# 5.maintainer
我自己(想一起玩的可以给我发消息)
# 6.license
小玩具,估计大佬也看不上,随便玩
# 7.expectation
  MVC分页支持...\
  reids模块要支持哨兵\\集群的连接(目前单机..)\
  jdbc的逆向工程相关要支持自动继承相关base接口,强制的前置条件可做自定义\
  oss完善...\
  mongoDB...\
  MQ...\
  ES...\
  日志相关...\
  cloud相关...
