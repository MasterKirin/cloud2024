package com.atguigu.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients  //@EnableFeignClients注解在Spring Cloud中的作用是启动Feign客户端的自动扫描和创建。‌ 当你在一个Spring Boot应用中添加了@EnableFeignClients注解，Spring Boot会自动扫描指定包路径下的所有标记了@FeignClient的接口，并为每个接口创建一个实现类，这个实现类实际上就是一个HTTP客户端，能够以声明式的方式调用远程服务‌
@SpringBootApplication
@EnableDiscoveryClient//enablediscoveryclient的注解是用于将服务注册到服务发现组件上,比如Eureka、consul、nacos、zookeeper等。使用这个注解后，服务会在启动时自动向注册中心注册自己的信息，其他服务通过查询注册中心获取可用的服务列表
public class Main84 {
    public static void main(String[] args) {
        SpringApplication.run(Main84.class,args);
    }
}
