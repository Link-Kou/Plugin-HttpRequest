# Plugin-HttpRequest

### Plugin-HttpRequest 能做什么？

> 基于OkHttp的retrofit2二次封装实现以下功能
- 基于Spring实现@Autowired注入
- 扩展输入输出解析，实现自定义拦截器
- 支持Spring环境和非Spring环境

## 同类

- `feign` Http请求工具[https://github.com/OpenFeign/feign](https://github.com/OpenFeign/feign)


场景
---
在于第三方对接中,HTTP请求是经常性使用的场景,JavaHttp请求的库有很多。
在初期选择的时候,就是以Android为环境作为参考的。因为Android经常性与后端进行请求对接。相关的库与使用经验上面都是齐备的。
后端在使用HTTP请求的时候,原来都是各种基于类似OkHttp这样子的框架进行封装。
导致的问题也比较多。在使用上面，也是不太方便。所以在工作中基于这一类的问题，进行了封装。
简化使用中的一些繁琐性的问题。

Maven
---
```xml
<dependency>
    <groupId>com.github.link-kou</groupId>
    <artifactId>http-request</artifactId>
    <version>1.0.7</version>
</dependency>
```

支持功能
---
- `SSL` 支持绝对路径输入证书。 `支持`
- `SSL` 支持资源路径输入证书。 `支持`
- `文件上传` 支持Spring文件流上传。 `支持`
- `文件上传` 支持本地文件流上传。 `支持`
- `文件上传` 支持文件上传进度监听。 `支持`
- `自定义@Body参数转换器以及返回参数的转换`  支持自定义转换器来实现对@Body的转换以及返回的转换 `支持`
- `添加XML解析`  支持XML的输入输出解析 `支持`
- `全局添加自定义请求参数` 自定义添加OKHttp拦截器 `支持`
- `日志输出` 日志开关问题。 `支持`
- `多服务请求` 合并多个请求服务。 `支持`
- `超时异常重试` 支持自定义超时重试次数。 `支持`
- `规定时间内请求缓存数据` 支持在一定的时间内请求同一个接口并且请求数据一致情况下缓存数据。 `不支持`
- `文件下载` 支持文件异步下载。 `支持`
- `文件断点下载` 支持文件异步断点下载。 `不支持`
- `OAuth` 多个接口请求之前需要携带特点的参数值。 `支持,未添加示列`

代码案例
---
`Spring配置`

```xml
<!-- HTTP请求配置-->
<bean class="com.linkkou.httprequest.spring.HTTPBeanProcessor">
    <!--扫描包名称-->
    <property name="prefix">
        <array value-type="java.lang.String">
            <value>com</value>
        </array>
    </property>
    <!--定义输入输出转换器-->
    <property name="httpConversion">
        <list>
            <bean class="com.linkkou.httprequest.extendPlugin.impl.HttpReturnJsonConversion"/>
            <bean class="com.linkkou.httprequest.extendPlugin.impl.HttpReturnXmlConversion"/>
        </list>
    </property>
    <!--定义拦截器-->
    <property name="okhttpInterceptor">
        <list>
            <bean class="com.Spring.interceptor.InterceptorCookiePlus"/>
        </list>
    </property>
    <!--配置文件-->
    <property name="locations">
        <list>
            <value>classpath*:*/*/httpurl.properties</value>
        </list>
    </property>
</bean>
```

```java
/**
 * @author lk
 * @version 1.0
 * @date 2020/9/11 11:29
 */
public class HttpRequestConfig {

    @Bean
    public HTTPBeanProcessor httpBeanProcessor() throws IOException {
        HTTPBeanProcessor httpBeanProcessor = new HTTPBeanProcessor();
        httpBeanProcessor.setPrefix(Arrays.asList("com").toArray(new String[0]));
        final HttpReturnJsonConversion httpReturnJsonConversion = new HttpReturnJsonConversion();
        httpBeanProcessor.setHttpConversion(Arrays.asList(httpReturnJsonConversion));
        httpBeanProcessor.setLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:httpurl.properties")
        );
        return httpBeanProcessor;
    }
}

```

`基本使用方式`
---
支持retrofit2所有使用方式。你会retrofit2就可以极低成本的在Spring环境中使用retrofit2。于此同时。借助retrofit2的齐全文档。有效解决学习成本问题

```java

/**
 * 高德API示列
 *
 * @author lk
 * @version 1.0
 * @date 2019/4/17 17:53
 */
@HTTPRequest(value = @Value("${amap.url}"))
public interface ApiAmap {

    /**
     * 高德 天气查询
     *
     * @param key        用户在高德地图官网申请web服务API类型KEY
     * @param city       输入城市的adcode，adcode信息可参考城市编码表
     * @param extensions 可选值：base/all base:返回实况天气 all:返回预报天气 可选性输入null
     * @param output     可选值：JSON,XML 可选性输入null
     * @return 天气对象
     */
    @GET("weather/weatherInfo")
    HTTPResponse<RepWeather> weather(@Query("key") String key,
                                     @Query("city") String city,
                                     @Query("extensions") String extensions,
                                     @Query("output") String output);

}

```





