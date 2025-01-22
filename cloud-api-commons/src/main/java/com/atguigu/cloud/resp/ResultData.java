package com.atguigu.cloud.resp;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResultData <T>{

    private String code;/** 结果状态 ,具体状态码参见枚举类ReturnCodeEnum.java*/
    private String message;
    private T data;
    private long timestamp;

    //接口返回数据的时间
    public ResultData() {
        this.timestamp = System.currentTimeMillis();
    }
    //接口返回的参数值-成功 success
    public static<T> ResultData<T> success(T data){
        ResultData resultData= new ResultData();
        resultData.setCode(ReturnCodeEnum.RC200.getCode());
        resultData.setMessage(ReturnCodeEnum.RC200.getMessage());
        resultData.setData(data);
        return resultData;
    }
    //接口返回的参数值-失败 fail
    public static<T> ResultData<T> fail(String code,String message){
        ResultData resultData= new ResultData();

        resultData.setCode(code);
        resultData.setMessage(message);
        resultData.setData(null);
        return resultData;
    }
}
