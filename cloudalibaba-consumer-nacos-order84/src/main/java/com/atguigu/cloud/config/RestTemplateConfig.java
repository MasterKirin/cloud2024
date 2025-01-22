package com.atguigu.cloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
//实例化 RestTemplate 实例
@Configuration
public class RestTemplateConfig {

    @Bean
    @LoadBalanced//赋予RestTemplate负载均衡的能力
    public RestTemplate restTemplate(){
        return  new RestTemplate();
    }
}
