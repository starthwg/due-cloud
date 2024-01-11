package com.due.cloud.service.auth.controller;

import com.due.basic.tookit.doamin.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @PostMapping("/test")
    public Result<String> test() {
        return Result.exec(() -> "ok");
    }
}
