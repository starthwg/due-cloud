package com.due.cloud.rpc.customer.service;

import com.due.basic.tookit.doamin.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *  customer服务
 */
@FeignClient(name = "ModuleCustomer")
public interface ICustomerService {

    @GetMapping("/customer/test/test01")
    Result<String> test01();
}
