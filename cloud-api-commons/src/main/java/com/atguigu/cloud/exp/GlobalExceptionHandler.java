package com.atguigu.cloud.exp;

import com.atguigu.cloud.resp.ResultData;
import com.atguigu.cloud.resp.ReturnCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@Slf4j
//@Slf4j注解的主要作用包括简化日志记录器的创建、提供不同日志级别的记录方法、自动添加类名和行号信息，以及简化日志配置的切换。
@RestControllerAdvice
// @RestControllerAdvice是 Spring Boot 提供的注解，用于实现全局范围内的异常处理和数据绑定设置。它基于 Spring MVC 的HandlerExceptionResolver和HandlerMethodReturnValueHandler实现，并通过 AOP 和事件机制来拦截和处理异常。这种机制使得全局异常处理和数据绑定可以集中管理，并提供了灵活性和扩展性。
public class GlobalExceptionHandler {
    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultData<String> exception(Exception e){
        System.out.println("#### come in GlobalExceptionHandler");
        log.error("全局异常信息：{}",e.getMessage(),e);
        return ResultData.fail(ReturnCodeEnum.RC500.getCode(), e.getMessage());
    }
}
