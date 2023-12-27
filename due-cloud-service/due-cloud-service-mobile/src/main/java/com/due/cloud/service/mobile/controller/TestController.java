package com.due.cloud.service.mobile.controller;

import com.due.basic.tookit.doamin.Result;
import com.due.cloud.service.mobile.service.TestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("/test01")
    public Result<String> testo1() {
        return Result.exec(this.testService::test01);
    }
}
