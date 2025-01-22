package com.atguigu.cloud.service.impl;

import com.atguigu.cloud.mapper.AccountMapper;
import com.atguigu.cloud.service.AccountService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService
{
    @Resource
    AccountMapper accountMapper;

    @Override
    public void decrease(Long userId, Long money) {
        log.info("------->account-service中扣减账户余额开始");
        accountMapper.decrease(userId,money);
        //设置超时
         //myTimeOut();
        //设置异常
        // int age = 10/0;
        log.info("------->account-service中扣减账户余额结束");
    }

    /**
     * 模拟超时异常，全局事务回滚
     * openFeign 默认读取数据超时时间是60秒，设置65秒则颐一定超时
     */
    private  static void myTimeOut()
    {
        try{
            TimeUnit.SECONDS.sleep(65);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
