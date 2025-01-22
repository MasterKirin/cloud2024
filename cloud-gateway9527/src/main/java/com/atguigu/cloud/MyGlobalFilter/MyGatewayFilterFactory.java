package com.atguigu.cloud.MyGlobalFilter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义网关过滤器规则
 * 参考GateWay内置出厂默认的过滤器，继承 AbstractGatewayFilterFactory类
 * - SetStatusGatewayFilterFactory
 * - SetPathGatewayFilterFactory
 * - AddResponseHeaderGatewayFilterFactory
 */
@Component //标注不可忘
public class MyGatewayFilterFactory extends AbstractGatewayFilterFactory<MyGatewayFilterFactory.Config> {
    //空参构造方法，内部调用super
    public MyGatewayFilterFactory(){
        super(MyGatewayFilterFactory.Config.class);
    }
    //重写apply方法 该方法内部重新定义规则(该方法规定访问的时候必须带atuguigu参数，否则访问结果是400)
    @Override
    public GatewayFilter apply(MyGatewayFilterFactory.Config config)
    {
        return new GatewayFilter()
        {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                ServerHttpRequest request = exchange.getRequest();
                System.out.println("进入自定义网关过滤器MyGateWayFilter,status==="+config.getStatus());
                if(request.getQueryParams().containsKey("atguigu"))
                {
                    return chain.filter(exchange);
                }else{
                    exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
                    return exchange.getResponse().setComplete();
                }
            }
        };
    }
    //重写shortcutFieldOrder
    @Override
    public List<String> shortcutFieldOrder() {
        List<String> list = new ArrayList<String>();
        list.add("status");
        return list;
    }

    public static class Config{
        @Setter@Getter
        private String status;
    }
}
