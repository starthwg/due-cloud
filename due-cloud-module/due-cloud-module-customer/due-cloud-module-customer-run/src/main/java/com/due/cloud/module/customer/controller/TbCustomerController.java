package com.due.cloud.module.customer.controller;


import com.due.basic.tookit.doamin.Result;
import com.due.bridge.tomcat.support.BasicController;
import com.due.cloud.module.customer.api.domain.response.Customer;
import com.due.cloud.module.customer.service.ITbCustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author HanWenGang
 * @since 2024-01-29
 */
@RestController
@RequestMapping("/customer")
@AllArgsConstructor
public class TbCustomerController extends BasicController {


    private ITbCustomerService customerService;

    @GetMapping("/one/{dataId}")
    public Result<Customer> oneByDataId(@PathVariable("dataId") Long dataId) {
        return Result.exec(() -> this.copy(this.customerService.selectDataByDataId(dataId), Customer.class));
    }
}

