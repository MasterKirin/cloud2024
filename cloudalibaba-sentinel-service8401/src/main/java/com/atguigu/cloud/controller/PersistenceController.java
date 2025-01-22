package com.atguigu.cloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PersistenceController {

    @GetMapping("/test")
    public String test(){
        return "-----test";
    }
    @GetMapping("/degrade")
    public String degrade(){
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
        return "------degrade 新增熔断规则-慢调用比例";
    }
    @GetMapping("/hot")
    public String hot(){
        return "-----test";
    }
}
