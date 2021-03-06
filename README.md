## 前言

搭建时每个项目的POM文件可以从以下[Gitee](https://gitee.com/lixiaogou/cloud2020/tree/master)中复制：


## 项目背景



### GIT

项目搭建全程手动git命令，文件的颜色表示如下状态

```
绿色，已经加入控制暂未提交
红色，未加入版本控制
蓝色，加入，已提交，有改动
白色，加入，已提交，无改动
灰色：版本控制已忽略文件；
```

#### 本地仓库搭建

```bash
# 初始话本地仓库
git init

# 关联远程仓库
git remote add <远程分支文件> <远程仓库地址>
git remote add origin ***SpringCloudAlibaba.git

# 拉取远程仓库文件，远程仓库建立时可能有自带的LICENSE/README.md
git pull origin main
```


#### 提交前查看文件状态

```bash
# 查看文件状态，可以列出那些文件被修改了
git status
```

![image-20220426175755785](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220426175755.png)

同步到GitHub分为多种方法，如果项目只有你一个人维护，你可以在每次写完之后全部提交到GitHub，走一套完整的流程，如果是多个人维护项目，如果你一次提交很多代码那么可能会与别人的代码发生冲突，所以此时我们就需要提交单个文件夹/文件

#### 提交单个文件

```bash
# 提交单个文件，只需要在后面加上要提交的文件名即可，剩下的流程就和提交到GitHub的步骤一样了
git add ./<文件名>

# 如下，git status 查看文件状态后发现
modified:   README.md
modified:   cloudAlibaba-consumer-order80/src/main/java/com/marshio/cloudAlibaba/controller/OrderController.java
modified:   cloudAlibaba-eureka/pom.xml
modified:   pom.xml

# 此时我想提交README.md

# 第一步，单独将README.md加到暂存区
git add ./README.md

# 第二步，提交到本地仓库
git commit -m "更新README.md"

# 第三步,提交到你的分支
git push remote main

# 同理，提交有较长路径的文件： OrderController.java，此时我们可以使用通配符
git add ./**/OrderController.java

# 提交一个文件夹或多个文件夹
git add cloudAlibaba-consumer-order80/src/main/java/com/marshio/cloudAlibaba/controller

# 提交多个文件夹
git add filePath1 filePath2 ... filePathN

# 如果想提交整个项目,
git add ./
```

#### 撤销提交

```bash
# 未使用add提交代码时，如果想撤销全部，将fileName替换成 。
git checkout fileName
git checkout .

# 使用add提交代码后，未commit之前
git reset HEAD fileName
git reset HEAD .

# 使用commit提交代码之后，未push之前，实验了一下，差点又得重码一边
git reset --h 

# 指定回退版本
git reset --h <commitid>

# 查看commitid
git log

# 退出log模式
q
```

#### 冲突解决

> 问题产生：本地修改代码后提交到远程仓库，本地版本回退，在提交，然后本地在回退到第一个版本，再次提交时发生如下问题
>
> ![image-20220506233311791](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/image-20220506233311791.png)
>
> 根源上还是本地与远程仓库代码发生了冲突，需要先拉取，在进行提交，但是此时我想保留本地版本，那么





## 项目架构

基础架构：Spring Cloud + Spring Cloud Alibaba + Spring Boot，采用分布式微服务系统架构，下面ca是`cloudAlibaba`的简称。

- 注册中心分别采用了eureka集群部署、Zookeeper、Consul

```xml
SpringcloudAlibaba
	--ca-commons				# 通用模块，用于存放通用业务实体类
	--ca-consumer-order80		# 消费者模块，使用restTemplate+ribbon进行服务调用
	--ca-consumer-consul-order80# 消费者模块，使用consul进行服务调用
	--ca-consumer-feign-order80	# 消费者模块，使用feign进行服务调用（OpenFeign）
	--ca-eureka7001				# eureka注册中心，单机版
	--ca-eureka7002				# eureka注册中心，与7003做成集群
	--ca-eureka7003				# eureka注册中心，与7002做成集群
	--ca-provider-payment8001 	# 服务提供，支付服务，注册到eureka7001
	--ca-provider-payment8002 	# 服务提供，支付服务，注册到eureka7002、7003，集群部署
	--ca-provider-payment8003 	# 服务提供，支付服务，注册到eureka7002、7003，DiscoverClient
	--ca-provider-payment8004 	# 服务提供，支付服务，注册到consul注册中心，
	--ca-provider-payment8005 	# 服务提供，支付服务，注册到eureka注册中心，使用Hystrix做服务降级、服务熔断
	--ca-provider-payment8010 	# nacos，服务注册，服务发现，配置中心
	--ca-
	--ca-
	--ca-
	--ca-
	--ca-
```






## 父项目

### 搭建过程

#### 1、new project

new project -> Maven项目,项目名称 **SpringcloudAlibaba**

#### 2、改POM

```xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <!--GAV  maven 坐标-->
  <groupId>com.marshio.springcloud</groupId>
  <artifactId>SpringcloudAlibaba</artifactId>
  <version>1.0-SNAPSHOT</version>
  <!-- 选择打包方式为pom -->
  <packaging>pom</packaging>

  <description>Demo project for Springboot + Spring Cloud + Spring Cloud Alibaba</description>


  <!-- 统一管理jar包版本 -->
  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
    <junit.version>4.12</junit.version>
    <log4j.version>1.2.17</log4j.version>
    <lombok.version>1.16.18</lombok.version>
    <mysql.version>5.1.47</mysql.version>
    <druid.version>1.1.16</druid.version>
    <mybatis.spring.boot.version>1.3.0</mybatis.spring.boot.version>
  </properties>

  <!-- 1、只是声明依赖，并不实际引入，子项目按需声明使用的依赖 -->
  <!-- 2、子项目可以继承父项目的 version 和 scope -->
  <!-- 3、子项目若指定了 version 和 scope，以子项目为准 -->
  <dependencyManagement>
    <dependencies>
      <!--spring boot 2.2.2-->
      <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-dependencies</artifactId>
        <version>2.2.2.RELEASE</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
      <!--spring cloud Hoxton.SR1-->
      <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-dependencies</artifactId>
        <version>Hoxton.SR1</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
      <!--spring cloud alibaba 2.1.0.RELEASE-->
      <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-alibaba-dependencies</artifactId>
        <version>2.1.0.RELEASE</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>

      <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>${mysql.version}</version>
      </dependency>
      <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid</artifactId>
        <version>${druid.version}</version>
      </dependency>
      <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
        <version>${mybatis.spring.boot.version}</version>
      </dependency>
      <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>${junit.version}</version>
      </dependency>
      <dependency>
        <groupId>log4j</groupId>
        <artifactId>log4j</artifactId>
        <version>${log4j.version}</version>
      </dependency>
      <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>${lombok.version}</version>
        <optional>true</optional>
      </dependency>
    </dependencies>
  </dependencyManagement>

  <!--Devtools热部署插件-->
  <build>
    <plugins>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <configuration>
          <fork>true</fork>
          <addResources>true</addResources>
        </configuration>
      </plugin>
    </plugins>
  </build>

</project>
```

> dependencyManagement 和 dependency 的区别
>
> dependencyManagement一般用于父类管理，父POM，可以让所有在子项目中引用一个依赖而不用显示的声明版本号
>
> Maven会沿着父子层次向上走，直到找到一个拥有dependencyManagement元素的项目，然后子项目就会使用dependencyManagement元素中指定的版本

#### 3、设置项目编码格式



#### 4、设置显示过滤文件

找到setting -> Editor -> File types，注意要用分号分开

![image-20220426171849129](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220426171849.png)


## 支付模块

### 搭建过程

#### 1、new model

new model -> Maven项目,模块名称：**cloudAlibaba-provider-order80**

#### 2、改POM

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>SpringcloudAlibaba</artifactId>
        <groupId>com.marshio.springcloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <description>支付模块，端口8001</description>

    <!--解决ignored pom.xml  file->settings->build,..->maven->ignored file->去掉勾即可-->
    <artifactId>cloudAlibaba-provider-payment8001</artifactId>


    <dependencies>

        <dependency>
            <groupId>com.marshio.springcloud</groupId>
            <artifactId>cloudAlibaba-commons</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <!-- web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- 监控 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!-- mybatis -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
        </dependency>

        <!-- alibaba.druid -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.2.8</version>
        </dependency>

        <!-- mysql -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>

        <!-- jdbc -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>

        <!-- devtools热部署工具 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>

        <!-- lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <!-- test -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <!--分布式跟踪系统，有助于收集解决微服务架构中得延迟问题所需的时序数据，它管理这些数据的收集和查找，包含了sleuth+zipkin-->
        <!--sleuth为分布式追踪提供了自动配置-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zipkin</artifactId>
        </dependency>

    </dependencies>
</project>
```

#### 3、建yml

在该模块下的resource文件下新建`application.yml`文件，注意，如果此时文件没有自动变成“齿轮炮”的状态，那么需要在maven那里刷新一下

#### 4、主启动



#### 5、业务类

#### 6、开启热部署

在微服务开发过程中，我们经常需要重启服务来重新编译代码进行测试，而重启服务又是一个很麻烦的事，我们可以开启一个热部署来自动重启修改过后的服务

##### 1、添加依赖

```xml
<!-- devtools热部署工具 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

##### 2、添加插件

> 注意：需要将以下插件放入父工程POM文件

```xml
<!--Devtools热部署插件-->
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <fork>true</fork>
                <addResources>true</addResources>
            </configuration>
        </plugin>
    </plugins>
</build>
```

##### 3、开启

> 在`setting->Build->Complier`路径下开启以下选项
>
> ![image-20220424172429880](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220424172454.png)

##### 4、Set Value

> 按如下快捷键 `ctrl+alt+shift+/`，选择`registry`，对如下内容打勾
>
> ![image-20220424172844712](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220424172844.png)

##### 5、重启IDEA

此时，IDEA的热部署就完成了

8、测试


## 消费模块

### 搭建过程

#### 1、new model

new model -> Maven项目，模块名称： **cloudAlibaba-provider-order80**

#### 2、改POM

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>SpringcloudAlibaba</artifactId>
        <groupId>com.marshio.springcloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>cloudAlibaba-consumer-order80</artifactId>


    <dependencies>

        <!-- web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- 监控 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!-- mybatis -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
        </dependency>

        <!-- alibaba.druid -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.2.8</version>
        </dependency>

        <!-- mysql -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>

        <!-- jdbc -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>

        <!-- devtools热部署工具 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>

        <!-- lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <!-- test -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <!--分布式跟踪系统，有助于收集解决微服务架构中得延迟问题所需的时序数据，它管理这些数据的收集和查找，包含了sleuth+zipkin-->
        <!--sleuth为分布式追踪提供了自动配置-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zipkin</artifactId>
        </dependency>

    </dependencies>
</project>
```

#### 3、建yml

在该模块下的resource文件下新建application.yml文件，注意，如果此时文件没有自动变成“齿轮炮”的状态，那么需要在maven那里刷新一下

```yaml
server:
  # 指定端口，服务端，供用户直接访问使用，所以使用默认的80端口
  port: 80

spring:
  application:
    # 服务名称
    name: cloudAlibaba-order-service
  datasource:
    # 数据源信息
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: org.gjt.mm.mysql.Driver
    url: jdbc:mysql://localhost:3306/cloudalibaba?useUnicode=true&characterEncoding=utf-8&useSSL=false
    username: root
    password: 20173602

mybatis:
  # mapper文件所在位置
  mapper-locations: classpath:mapper/*.xml
  # 实体类所在文件
  type-aliases-package: com.marshio.cloudAlibaba.entities
```

#### 4、主启动

- 在Java文件夹下新建**Order80Application**的Java类
- 添加```@SpringBootApplication```的注解
- 添加```main```方法
- 添加如下代码 ```SpringApplication.run(OrderApplication.class,args);```

#### 5、业务类

注意，由于我们当前的订单业务，是直接去调用Payment的接口，并没有实际性的业务，所以我们只需要写一个```XXX Controller```去调用```PaymentController```即可.
这就是所谓的**服务调用** 



##### RestTemplate

是HTTP Client的封装版

#### 6、开启热部署

同上



#### 7、测试

请求：





#### 错误

1、 **Expected scheme-specific part at index 10: localhost:**
    这是由于自定义的url未拼接 ``` http:// ```


## 工程重构

由于两个模块中存在相同的类，这样当系统很大时，就会造成系统代码冗余，所以我们需要整理各子模块都能用到的重复代码，将其放在一个通用模块下，然后当模块想要使用其中的类时，可以通过pom文件将其导入。

冗余代码

![image-20220426133620940](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220426133621.png)



### 重构过程

1、新建commons模块

![image-20220426133814180](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220426133814.png)

2、将通用代码移到改commons模块中

3、通过pom文件将commons模块引入

```xml
<!--引入自定义的通用包-->
<dependency>
    <groupId>com.marshio.springcloud</groupId>
    <artifactId>cloudAlibaba-commons</artifactId>
    <version>${project.version}</version>
</dependency>
```

## Eureka注册中心

### 简介



详情请看 [Eureka](https://github.com/Masuo777/SpringCloudAlibaba/blob/main/cloudAlibaba-eureka7001/README.md).

### 搭建过程

#### 1、new model

new model -> Maven项目,模块名称：**cloudAlibaba-eureka7001**

#### 2、改POM

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>SpringcloudAlibaba</artifactId>
        <groupId>com.marshio.springcloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>cloudAlibaba-eureka</artifactId>

    <description>使用eureka作为注册中心</description>


    <dependencies>
        <!-- 服务注册中心的服务端 eureka-server -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>

        <!-- web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- actuator  -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

    </dependencies>
</project>
```

#### 3、建yml

在该模块下的resource文件下新建`application.yml`文件，注意，如果此时文件没有自动变成“齿轮炮”的状态，那么需要在maven那里刷新一下

```yaml
server:
  # 服务端口
  port: 7001

spring:
  application:
    # 服务名称
    name: eurake7001

eureka:
  instance:
    # eureka服务端的实例名称
    hostname: eurekaServe
  client:
    # 是否将自己注册给eureka注册中心，由于自己就是服务端，所以不需要将自己注册给自己
    register-with-eureka: false
    # 服务发现，是否从Eureka注册中心获取服务列表，由于自己是服务端，无需发现服务
    fetch-registry: false
    service-url:
      # K-V，设置eureka server的地址，注意defaultZone并不是属性，只是K-V里的key,单机模式下只需要写自己的Eureka服务地址即可，注意加上 /eureka 后缀
      defaultZone: http://localhost:7001/eureka
```

#### 4、主启动

- 在Java文件夹下新建 **com.marshio.cloudAlibaba.Eureka7001Application** 的Java类.
- 添加```@SpringBootApplication```的注解
- 添加```@EnableEurekaServer```的注解，标注这是Eureka服务端
- 添加```main```方法
- 添加如下代码 ```SpringApplication.run(OrderApplication.class,args);```

#### 5、业务类

无

#### 6、测试

打开如下地址： http://localhost:7001/，注意这里请求Eureka服务的页面时，不需要加eureka的后缀，因为前端过滤器会自己定向到Eureka的页面，出现类似下面的图即可，因为现在还未注册服务，所以在服务实例这里还没有实例

![image-20220428143044737](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220428143045.png)

#### 7、注册Order服务

##### 1、修改POM

找到服务提供者（这里我们假设Order也是服务提供者之一）的POM文件，添加如下依赖

```xml
<!-- 服务注册中心的客户端 eureka-client -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

##### 2、修改YML

找到服务提供者的YML文件，添加如下代码

```yaml
eureka:
  client:
    # 表示自己需要被注册到注册中心
    register-with-eureka: true
    # 表示自己需要发现服务，即可能会通过注册中心调用服务
    fetch-registry: true
    service-url:
      # Eureka服务端的地址，
      defaultZone: http://localhost:7001/eureka
```

##### 3、修改启动类

找到客户端的主启动类，在类上添加注释```@EnableEurekaClient```，标注这是一个Eureka客户端，如下

```java
// 标识这是一个Eureka客户端
@EnableEurekaClient
@SpringBootApplication
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }
}
```

##### 4、测试

打开Eureka注册中心的页面，能发现多了一个服务

![image-20220427160858441](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220427160858.png)

#### 8、注册Payment服务

同上

#### 9、Payment服务集群

##### 1、新建模块

新建一个名为 **cloudAlibaba-provider-payment8002** 的 model，过程参考 [支付模块](# 支付模块)

##### 2、修改POM

复制8001模块的POM文件

##### 3、修改YML

```yaml
server:
  # 服务端口号
  port: 8002

spring:
  application:
    # 服务名称,相同服务需要设置相同的name
    name: cloudAlibaba-payment-service
  datasource:
    # 数据源信息
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: org.gjt.mm.mysql.Driver
    url: jdbc:mysql://localhost:3306/cloudalibaba?useUnicode=true&characterEncoding=utf-8&useSSL=false
    username: root
    password: 20173602
    druid:
      test-while-idle: false

eureka:
  client:
    # 注册服务，将自己向注册中心注册时，
    register-with-eureka: true
    # 发现服务
    fetch-registry: true
    # Eureka服务端地址
    service-url:
      # 这里必须要加 /eureka 的后缀，不然会被过滤掉，导致找不到注册中心
      defaultZone: http://eurekaServe7001.com:7001/eureka,http://eurekaServe7002.com:7002/eureka

  instance:
    hostname: payment8002
    prefer-ip-address: true
    # 服务实例id可自定义，可默认：主机名+服务名+port
    instance-id: ${eureka.instance.hostname}-${spring.application.name}:${server.port}

mybatis:
  # mapper文件所在位置
  mapper-locations: classpath:mapper/*.xml
  # 实体类所在文件
  type-aliases-package: com.marshio.cloudAlibaba.entities
```

##### 4、启动服务

启动所有服务，如下

![image-20220427174106938](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220427174107.png)

##### 5、问题

在复制8001模块的POM文件后，发现一个问题，就是8002的Java/resource文件变成了8001的model文件，删除模块新建还是没用

如下：

![image-20220428091944325](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220428091944.png)

在上图中：8003的Java文件和resource文件后面跟着【cloudAlibaba-provider-payment8002】，这是因为我是直接复制的8002的pom文件，然后一刷新maven，maven一读取新的pom文件的GAV坐标，发现这是8002的pom，所以它会将8003文件下的Java文件和resource文件注册到8002文件下，写在了一个文件里，具体是哪我就不知道了，但是解决方法还是有的。

首先，修改`cloudAlibaba-provider-payment8003`文件下的pom文件中的坐标

![image-20220428092418579](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220428092418.png)

然后，打开项目结构（model structure），你可以先查看源目录，如果这是目前的目录，就不用删，如果不是，就删掉。

![image-20220428092859732](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220428092900.png)

##### 6、测试

此时发现，`CLOUDALIBABA-PAYMENT-SERVICE`服务对应着多个payment服务

![image-20220428143044737](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220428143045.png)

集群就搭建完成，顺带测试一下是否能正常请求数据。

##### 7、请求数据

既然有了集群部署，服务消费者在请求数据时就不能再通过固定的【ip+端口】去请求了，而是通过注册中心去请求服务，在注册中心中，能以一概全的包括所有Payment微服务的就只有【Application】这个标签了，所以我们就需要通过这个标签对应的值去请求服务。
这就是负载均衡，服务消费者在调用服务时，

即修改如下

```java
// 原请求地址：ip + port
// public static final String PAYMENT_URL = "http://localhost:8001";

// 集群服务下直接向Eureka调用服务，新请求地址
public static final String PAYMENT_URL = "http://CLOUDALIBABA-PAYMENT-SERVICE";
```

这一请求数据会发现，请求失败，错误状态是500，即服务器出现了问题，原因是我们虽然添加了集群服务，但是此时，服务并不知道我们请求的是服务集群，他只是单纯的知道我们请求的是【http://CLOUDALIBABA-PAYMENT-SERVICE/payment/getPaymentById/11】，也就是他不知道向服务中心去请求服务，也就解析不了请求地址，所以会报500错误。

![image-20220428143835631](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220428152043.png)

**解决办法**

我们需要给```RestTemplate```加一个```@LoadBanlanced```的注解，因为我们是通过```RestTemplate```发起远程服务调用，此注解帮助他将请求发送到服务中心，并解析地址，然后再请求具体的服务集群中的某一台机器

```java
@Bean
@LoadBalanced// 如果直接向注册中心调取服务的话，需要加上此注解
public RestTemplate getRestTemplate() {
    return new RestTemplate();
}
```

#### 10、注册中心集群

##### 1、新建模块

新建一个名为 **cloudAlibaba-eureka7002** 的 model，过程参考 [Eureka注册中心](# Eureka注册中心)

##### 2、修改POM

复制7001模块的POM文件

##### 3、修改YML

```yaml
server:
  port: 7002

spring:
  application:
    name: eureka7002

eureka:
  instance:
    hostname: eurekaServe7002.com

  client:
    register-with-eureka: false
    fetch-registry: false
    service-url:
      defaultZone: http://eurekaServe7001.com:7001/eureka
```

##### 4、建立主启动类

设置新模块的端口号为**非8001**端口

##### 5、修改host（可选）

如果你不是在单机环境下部署集群，可忽略，如果你是单机环境下，且Eureka的hostname是默认的，也可忽略。

如果你是单机环境下部署集群，且自定义Eureka的hostname，则需要修改host，将localhost映射到hostname。

如下

![image-20220428151135180](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220428151135.png)



**注意**：

在这里可能会出现一个问题：`UnknownHostException: ??: ??`

```java
2022-05-10 23:21:46.659  WARN 27924 --- [  restartedMain] c.n.e.transport.JerseyReplicationClient  : Cannot find localhost ip

java.net.UnknownHostException: ??: ?? // 这里的错误纯属是因为你的电脑hostname是不能正常解析的，所以显示乱码，
```

1、类似如上的错误，基本就是因为你的host文件以及yml文件配置出现了冲突，

根据源码追踪：

```java
public EurekaInstanceConfigBean(InetUtils inetUtils) {
    // 2、设置默认的 host info 
    this.hostInfo = this.inetUtils.findFirstNonLoopbackHostInfo();
    this.ipAddress = this.hostInfo.getIpAddress();
    // 1、设置默认的hostname，看上面
    this.hostname = this.hostInfo.getHostname();
}

// 3、设置第一个非回调地址信息
public InetUtils.HostInfo findFirstNonLoopbackHostInfo() {
    InetAddress address = this.findFirstNonLoopbackAddress();
    if (address != null) {
        return this.convertAddress(address);
    } else {
        InetUtils.HostInfo hostInfo = new InetUtils.HostInfo();
        // 4、设置默认的hostname
        hostInfo.setHostname(this.properties.getDefaultHostname());
        hostInfo.setIpAddress(this.properties.getDefaultIpAddress());
        return hostInfo;
    }
}

// 5、默认的hostname为localhost
private String defaultHostname = "localhost";
```

我们如果想解决这个问题的话，需要在host文件中如下设置

> 注意，需要让`hostname`和`hosts文件中的name`相匹配

![image-20220510234534237](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/image-20220510234534237.png)



2、当然也可能是你的电脑的hostname确实乱码了

比如我的：

![image-20220510234851640](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/image-20220510234851640.png)

这样也会导致出现上面的问题。

##### 6、注册中心复制

在模块```cloudAlibaba-eureka7001```下的```application.yml```文件下添加如下

```yaml
eureka:
  instance:
    hostname: eurekaServe7001.com

  client:
    register-with-eureka: false
    fetch-registry: false
    service-url:
      # 告诉 eurekaServe7002
      defaultZone: http://eurekaServe7002.com:7002/eureka
```

在模块```cloudAlibaba-eureka7002```下的```application.yml```文件下添加如下

```yaml
eureka:
  instance:
    hostname: eurekaServe7002.com

  client:
    register-with-eureka: false
    fetch-registry: false
    service-url:
      # 告诉 eurekaServe7002
      defaultZone: http://eurekaServe7001.com:7001/eureka
```

打开页面出现如下

![image-20220428151947292](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220428151947.png)





## Consul注册中心

### 简介



详情请看：[Consul]()

### 搭建过程

#### 1、下载consul

去[consul官网](https://www.consul.io/downloads)下载对应版本的consul服务端。

如果你是64位，选择AMD64.

![image-20220513110838030](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220513110904.png)

#### 2、启动consul

下载完之后，解压缩，在有`consul.exe`的地方打开`cmd`，输入`consul agent -dev`。

或者你可以直接在我这里的附件里找到consul，双击`runConsul.bat`。

至此，consul的服务端就启动完成了，你可以访问`localhost:8500`测试一下。

![image-20220513111123059](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220513111123.png)

![image-20220513111308660](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220513111308.png)

#### 3、注册payment服务

##### 1、new model

在这里，我们需要将**服务提供者**注册到consul服务端。

new model -> Maven项目,模块名称：**cloudAlibaba-provider-payment8004**.

##### 2、改pom

```xml
<dependencies>

    <!--consul客户端，服务注册与服务发现-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-consul-discovery</artifactId>
    </dependency>

    <dependency>
        <groupId>com.marshio.springcloud</groupId>
        <artifactId>cloudAlibaba-commons</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
    <!-- web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- 健康检查 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>

    <!-- mybatis -->
    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
    </dependency>

    <!-- alibaba.druid -->
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid-spring-boot-starter</artifactId>
        <version>1.2.8</version>
    </dependency>

    <!-- mysql -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>

    <!-- jdbc -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jdbc</artifactId>
    </dependency>

    <!-- devtools热部署工具 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>

    <!-- lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>

    <!-- test -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>

    <!--分布式跟踪系统，有助于收集解决微服务架构中得延迟问题所需的时序数据，它管理这些数据的收集和查找，包含了sleuth+zipkin-->
    <!--sleuth为分布式追踪提供了自动配置-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-zipkin</artifactId>
    </dependency>

</dependencies>
```

##### 3、改yml

```yaml
server:
  port: 8004

spring:
  application:
    name: cloudAlibaba-payment-service
  cloud:
    consul:
      host: localhost
      port: 8500
      discovery:
        # 向 consul 注册
        service-name: ${spring.application.name}
```

##### 4、主启动

略

##### 5、测试

> 注意，启动客户端前一定要先启动consul的服务端。

访问 `http://localhost:8500/ui/dc1/services`

![image-20220513141056048](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220513141056.png)

#### 4、调用payment服务

##### 1、new model

在这里，我们需要将**服务消费者**注册到consul服务端。

new model -> Maven项目,模块名称：**cloudAlibaba-consumer-consul-order80**.

##### 2、改pom

```xml
<dependencies>

    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-consul-discovery</artifactId>
    </dependency>

    <!--引入自定义的通用包-->
    <dependency>
        <groupId>com.marshio.springcloud</groupId>
        <artifactId>cloudAlibaba-commons</artifactId>
        <version>${project.version}</version>
    </dependency>

    <!-- web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- 监控 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>

    <!-- devtools热部署工具 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>

    <!-- lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>

    <!-- test -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>

    <!--分布式跟踪系统，有助于收集解决微服务架构中得延迟问题所需的时序数据，它管理这些数据的收集和查找，包含了sleuth+zipkin-->
    <!--sleuth为分布式追踪提供了自动配置，如果报错可以注掉，等你有了解后可以配置zipkin-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-zipkin</artifactId>
    </dependency>

</dependencies>
```



##### 3、建yml

```yaml
server:
  port: 80

spring:
  application:
    name: consul-consumer-order
  # 使用本机注册时，可以省略，因为下面这些都是默认的，当然如果需要向集群注册，那么就需要使用下面的配置 
  cloud:
    consul:
      host: localhost
      port: 8500
      discovery:
        service-name: ${spring.application.name}
```



##### 4、主启动

```java
@SpringBootApplication
@EnableDiscoveryClient
// 向 eureka/zookeeper/consul 注册服务，发现服务时需要使用这个注解，当然eureka也可以使用自己的注解
public class Order80Application {

    public static void main(String[] args) {
        SpringApplication.run(Order80Application.class, args);
    }
}
```



##### 5、测试

建立controller

```java
@RestController
public class OrderController {
	// 向注册中心调取服务
    public static final String PAYMENT = "http://consul-provider-payment";

    @Resource
    DiscoveryClient discoveryClient;

    @RequestMapping("/consumer/get")
    public ResponseBean<List<String>> getPayment() {
        List<String> services = discoveryClient.getServices();
        List<String> stringList = new ArrayList<>(services);

        return new ResponseBean<>(stringList);
    }
}
```



访问`http`

## OpenFeign服务调用

### 简介

详情请看[OpenFeign](https://github.com/Masuo777/SpringCloudAlibaba/tree/main/cloudAlibaba-consumer-feign-order80/README.md).


### 搭建过程

#### 1、new model

> Feign只能在服务消费者方使用

new model -> Maven项目,模块名称：**cloudAlibaba-consumer-feign-order80**

#### 2、改pom

```xml
<dependencies>
    <!-- OpenFeign -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>

    <!-- OpenFeign需要和 Eureka 联合使用，服务注册中心的客户端 eureka-client -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>

    <!--引入自定义的通用包-->
    <dependency>
        <groupId>com.marshio.springcloud</groupId>
        <artifactId>cloudAlibaba-commons</artifactId>
        <version>${project.version}</version>
    </dependency>

    <!-- web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- 监控 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>

    <!-- devtools热部署工具 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>

    <!-- lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>

    <!-- test -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>

    <!--分布式跟踪系统，有助于收集解决微服务架构中得延迟问题所需的时序数据，它管理这些数据的收集和查找，包含了sleuth+zipkin-->
    <!--sleuth为分布式追踪提供了自动配置，如果报错可以注掉，等你有了解后可以配置zipkin-->
    <!--<dependency>-->
    <!--    <groupId>org.springframework.cloud</groupId>-->
    <!--    <artifactId>spring-cloud-starter-zipkin</artifactId>-->
    <!--</dependency>-->

</dependencies>
```

#### 3、改yml

```yaml
server:
  port: 80

eureka:
  client:
    # 服务消费者，可以不进行服务注册
    register-with-eureka: false
    service-url:
      # 严格遵守 key: value 的形式
      defaultZone: http://eurekaServe7002.com:7002/eureka/,http://eurekaServe7003.com:7003/eureka/
```

#### 4、主启动

- 在Java文件下新建 **com.marshio.cloudAlibaba.OrderFeign80Application** 的类.
- 添加```@SpringBootApplication```的注解
- 添加```@EnableFeignClient```的注解，标注这个模块需要开启Feign的服务调用功能
- 添加```main```方法

#### 5、创建与服务对应的接口

> 新建service包，因为我们需要调用的是Payment的服务，所以我们需要找到Payment的服务接口类，并将其copy一份，放在OrderFeign模块下。
>
> 然后添加```@Component``` 和 ```@FeignClient(value = "服务的Application_name")```。
>
> 然后需要注意的是，我们要遵循调用规范：Acontroller调用Aservice，如果A服务需要调用B服务，那么需要用Aservice调用Bcontroller，再由Bcontroller调用Bservice。

比如，我的Payment服务接口如下
```java
@Service
public interface PaymentService {

    /**
     * @param id by
     * @return Payment
     */
    Payment getPaymentById(@Param("id") Long id);
}
```

那么我在调用时，就需要在OrderFeign模块下，建立对应的接口

```java
@Component
// 声明这是使用Feign进行服务调用，且调用的服务为 CLOUDALIBABA-PAYMENT-SERVICE，由负载均衡自动去寻找可用服务
@FeignClient(value = "CLOUDALIBABA-PAYMENT-SERVICE")
public interface PaymentFeignService {

    // 服务提供方有什么接口功能，我们这里就提供什么功能

    /**
     * 根据订单id获取订单信息,
     * 调用过程，消费者发起调用-》controller --》 service ，找到CLOUDALIBABA-PAYMENT-SERVICE服务下的 /payment/getPaymentById/{id}地址对应的接口 --》调用服务提供方的controller
     * Feign会根据【服务名称 + GetMapping的value】去调用对应的服务
     * @param id 订单id
     * @return ResponseBean 对返回值进行封装
     */
    @GetMapping(value = "/payment/getPaymentById/{id}")
    ResponseBean<Payment> getPaymentById(@PathVariable("id") Long id);
}
```

#### 6、调用接口服务

> 调用服务时，就把服务当成本地服务去调用即可，这也是为什么说，Feign是声明式服务调用，只需要简单的声明服务接口，就可以进行调用。

```java
@RestController
public class PaymentFeignController {

    // 依赖注入，等同于@Autuwired
    @Resource
    PaymentFeignService paymentFeignService;

    @GetMapping(value = "/consumer/get/{id}")
    public ResponseBean<Payment> getPaymentById(@PathVariable("id") Long id) {
        return paymentFeignService.getPaymentById(id);
    }
}
```

### 超时控制

OpenFeign是基于Ribbon和Hystrix开发的，所以它自带超时控制。

其默认是3s无响应就会抛出异常，但是我们实际使用过程中，有很多服务由于各种原因都不能再给定的时间内给出响应，所以我们就需要设置更长的超时时间。

#### 更改ml文件

```yaml
ribbon:
  # 指建立连接所用的时间 ms
  ReadTimeout: 5000
  # 从服务读取资源所用时间(不包括建立连接) ms
  ConnectTimeout: 5000
```

#### 测试

我们可以在服务提供方写一个方法，专门用于超时响应测试，代码如下。

```java
/*
 * 超时测试
 * @return port
 */
@GetMapping(value = "/payment/timeOutTest")
public String timeOut() {
    try {
        // 无任何业务操作，只是单纯的线程休眠3s
        Thread.sleep(3000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    return port;
}
```




## Hystrix

### 简介

Hystrix，断路器，是一个用于处理分布式系统的延迟和容错的开源库，在分布式系统里，许多服务会不可避免地调用失败，比如超市、异常等，Hystrix能保证在一个服务出问题的情况下，不会导致整体服务失败，避免级联故障，提高分布式系统的弹性。



详情查看：[Hystrix]().

### 搭建过程



#### 1、new model

new model -> Maven项目,模块名称：**cloudAlibaba-provider-payment8005**

#### 2、改pom

```xml
<dependencies>

        <!--Hystrix 服务降级、服务熔断-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
        </dependency>

        <!--Eureka注册中心-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>com.marshio.springcloud</groupId>
            <artifactId>cloudAlibaba-commons</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <!-- web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- 监控 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!-- mybatis -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
        </dependency>

        <!-- alibaba.druid -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.2.8</version>
        </dependency>

        <!-- mysql -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>

        <!-- jdbc -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>

        <!-- devtools热部署工具 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>

        <!-- lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <!-- test -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <!--分布式跟踪系统，有助于收集解决微服务架构中得延迟问题所需的时序数据，它管理这些数据的收集和查找，包含了sleuth+zipkin-->
        <!--sleuth为分布式追踪提供了自动配置-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zipkin</artifactId>
        </dependency>
    </dependencies>
```

#### 3、改yml

```yaml
server:
  port: 8005

spring:
  application:
    name: cloudAlibaba-payment-service

  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: org.gjt.mm.mysql.Driver
    url:
    username: masuo
    password: Mas+1006

# eureka和Hystrix配合使用
eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://localhost:7001/eureka/
  instance:
    instance-id: payment8005
```

#### 4、主启动

- 在Java文件下新建 **com.marshio.cloudAlibaba.Payment8005Application** 的类.
- 添加```@SpringBootApplication```的注解
- 添加```@EnableDiscoveryClient```的注解
- 添加```main```方法





## Nacos



### 简介

参考[什么是Nacos](https://nacos.io/zh-cn/docs/what-is-nacos.html)。



### 搭建

#### 1、new model

new model -> Maven项目,模块名称：**cloudAlibaba-provider-payment8010**

#### 2、改pom

```xml
<!-- nacos，注册中心、服务发现、配置中心 -->
<!-- 注册中心，服务发现功能 -->
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-alibaba-nacos-discovery</artifactId>
</dependency>

<!-- 配置中心功能 -->
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-alibaba-nacos-config</artifactId>
</dependency>
```

#### 3、改yml

`application.yml`

```yaml
# 如过只需要服务发现，注册中心的功能，那么下面的配置就够用了
server:
  port: 8010

spring:
  application:
    name: nacos-service
  cloud:
    nacos:
      discovery:
        # 客户端注册到nacos的名称，默认为 ${spring.cloud.nacos.discovery.service:${spring.application.name:}}
        service: cloudAlibaba-payment-service
        # nacos 服务端地址
        server-addr: 127.0.0.1:8848
        # 隔离不同的服务实例
        namespace: public
```

> 想要Nacos的配置中心功能起作用，就必须要在bootstrap.yml中配置Nacos，因为在Spring Cloud中bootstrap.yml文件的加载是优先级最高的，这样才能保证动态加载配置文件。
>
> bootstrap.yml是Spring Cloud扩展出来的配置文件，他会默认去尝试加载`${spring.application.name}.properties`配置文件，所以我们可以将一些公共配置文件放在该配置文件中。
>
> 这里我们会涉及到多个配置文件，那我们就不得不说一下配置文件的加载顺序。
>
> - 首先，上面我们提到了bootstrap.yml的优先级是最高的，所以优先加载.
> - 其次，会加载application.yml文件
> - 最后，按照顺序从上往下加载其余配置文件
>
> 然后配置文件遵循优先级大的会覆盖优先级小的，并且会形成互补.

`bootstrap.yml`

```yaml
# 此文件尽量只放配置中心相关配置
spring:
  cloud:
    nacos:
      config:
        server-addr: 127.0.0.1:8848
        # 使用非默认的properties文件时，需要设置文件扩展名，只针对默认配置文件
        file-extension: yml
        
        # 当我们需要加载公共配置文件时，我们可以使用shared-dataids,且这些配置文件的优先级较低
        shared-dataids: ext-conf1.yml,ext-conf2.yml,ext-conf3.yml
        
        # 或者
        # 使用ext-config，这是一个list，优先级要高于shared-dataids
        ext-config[0]:
          data-id: xxx0.yml
          refresh: true
        ext-config[1]:
          data-id: xxx1.yml
```

综上，优先级排序：profile > 默认配置文件 > ext-conf > shar



#### 4、主启动

- 在Java文件下新建 **com.marshio.cloudAlibaba.Payment8005Application** 的类.
- 添加```@SpringBootApplication```的注解
- 添加```@EnableDiscoveryClient```的注解
- 添加```main```方法





