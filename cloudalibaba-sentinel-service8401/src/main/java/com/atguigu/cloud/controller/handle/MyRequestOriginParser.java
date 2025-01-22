package com.atguigu.cloud.controller.handle;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

/**
 *  RequestOriginParser 中的- parseOrigin方法来说实现 origin参数的传入，告诉httpServletRequest携带的是什么参数，是黑名单还是白名单，放行还是静止。
 */
@Component
public class MyRequestOriginParser implements RequestOriginParser {

    @Override
    public String parseOrigin(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getParameter("serverName");
    }
}
