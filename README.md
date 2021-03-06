# nobug-shop 成氏集团项目组
## 公告栏
```
为了发扬秦始皇统一度量衡的精神

新建子项目的包名统一为com.nobug

新建的子服务带上父项目名，例如：
登录服务的cloud项目命名为nobug-shop-service-login

新建的cloud服务pom里统一继承此父类配置文件：
    <parent>
        <groupId>com.nobug</groupId>
        <artifactId>nobug-shop-dependencies</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </parent>
    
    并且可以删除以下属性：
        <properties>
            <java.version>1.8</java.version>
            <spring-cloud.version>Hoxton.SR1</spring-cloud.version>
        </properties>
        
注意：删掉后SpringCloud会统一使用Hoxton.SR1！
如果是默认生成Hoxton.SR3,可能导致无法自动导config server包的情况。
```
## 端口占用记录汇总：
```
service:
    eureka server 8761
    send mail service 8762
    payment 8765
    service search 7777 
    send SMS 8763
    down order server 7779
    
    
web:
    rigester web 8081
    order web 80
    search web 7778 
    login web 8082
    down order web 7780
    index web 8080
   
mapper:
    user info mapper 8100
    product mapper 8101

cache:
    redis register 6301
    redis login 6302
    redis product 6303
    redis cart 6304

祝各位好运，肝就完了！

```