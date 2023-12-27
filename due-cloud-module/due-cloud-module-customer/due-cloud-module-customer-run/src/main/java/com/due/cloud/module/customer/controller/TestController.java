package com.due.cloud.module.customer.controller;


import com.due.basic.tookit.doamin.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/test01")
    public Result<String> test01(){
       return Result.exec(() -> "ok" );
    }
}
