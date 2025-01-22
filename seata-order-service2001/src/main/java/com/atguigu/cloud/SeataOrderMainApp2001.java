package com.atguigu.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.atguigu.cloud.mapper")//import tk.mybatis.spring.annotation.MapperScan;
@EnableDiscoveryClient//服务器注册与发现
@EnableFeignClients//注解添加到主类或配置类上，可以启用Feign客户端的自动发现和创建。Spring Cloud会自动扫描指定包及其子包中的所有标记了@FeignClient的接口，并为每个接口创建一个动态代理实现，该实现会根据配置的参数和方法调用，自动构造HTTP请求‌
public class SeataOrderMainApp2001
{
    public static void main(String[] args) {
        SpringApplication.run(SeataOrderMainApp2001.class,args);
    }
}
