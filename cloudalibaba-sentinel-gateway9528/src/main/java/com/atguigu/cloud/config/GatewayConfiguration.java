package com.atguigu.cloud.config;

import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.http.codec.ServerCodecConfigurer;
import javax.annotation.PostConstruct;
import java.util.*;

@Configuration
public class GatewayConfiguration
{
    private final List<ViewResolver> viewResolvers;
    private final ServerCodecConfigurer serverCodecConfigurer;

    public GatewayConfiguration(ObjectProvider<List<ViewResolver>> viewResolversProvider, ServerCodecConfigurer serverCodecConfigurer)
    {
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
        // Register the block exception handler for Spring Cloud Gateway.
        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
    }

    @Bean
    @Order(-1)
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }
    @PostConstruct //javax.annotation.PostConstruct
    public void doInit() {
        initBlockHandler();
    }
//=========================================================================================以上为网关默认配置
    /**
     * =================================================================gateway网关配置自定义
     * gateway网关配置自定义路由规则，限流返回值定制
     * 1.自定义sentinel路由规则:控、熔断、热点、授权
     * 2.自定了限流返回参数定制:BlockRequestHandler
      */
    private  void  initBlockHandler()
    {
        Set<GatewayFlowRule> rules = new HashSet<>();
        //可在此添加多过自定义规则要与 Gateway routes中的id相对应，规则参数为sentinel中的流控、熔断、热点、授权等规则。
        rules.add(new GatewayFlowRule("pay_routh1")
                .setCount(2)//根据流控参数进行定义规则
                .setIntervalSec(1)
        );
        //可在此添加多过自定义规则要与 Gateway routes中的id相对应
//        rules.add(new GatewayFlowRule("httpbin_route")

        GatewayRuleManager.loadRules(rules);//将编写的sentinel路由规则加载到网关中.
        BlockRequestHandler handler = new BlockRequestHandler() {//设置的自定义限流响应值定制
            @Override
            public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable throwable) {
                Map<String, String> map = new HashMap<>();
                map.put("errorCode", HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase());//访问太多
                map.put("errorMessage","请求太过频繁，系统忙不过来，触发限流(sentinel+gataway整合Case)");
                return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(map));
            }
        };
        GatewayCallbackManager.setBlockHandler(handler);//gateway回调方法-添加设置的自定义限流响应值定制
    }
}
