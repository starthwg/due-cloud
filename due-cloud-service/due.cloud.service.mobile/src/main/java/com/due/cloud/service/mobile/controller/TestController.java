package com.due.cloud.service.mobile.controller;

import com.due.cloud.service.mobile.service.TestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("/test01")
    public String testo1() {
        return testService.test01();
    }
}
