package com.atguigu.cloud.apis;

import com.atguigu.cloud.resp.ResultData;
import com.atguigu.cloud.resp.ReturnCodeEnum;
import org.springframework.stereotype.Component;

/**
 * 为新增远程调用新建全局统一服务降级类 fallback = PayFeignSentinelApiFallBack.class
 * openFeign 与Sentinel整合-全局fallback处理服务器降级
 */
@Component
public class PayFeignSentinelApiFallBack implements PayFeignSentinelApi
{
    @Override
    public ResultData getPayByOrderNo(String orderNo) {
        return ResultData.fail(ReturnCodeEnum.RC500.getCode(), "对方服务器宕机或不可用，fallBack服务降级o(╥﹏╥)o");
    }
}
