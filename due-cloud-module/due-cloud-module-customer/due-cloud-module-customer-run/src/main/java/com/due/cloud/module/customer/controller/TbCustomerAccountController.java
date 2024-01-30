package com.due.cloud.module.customer.controller;


import com.due.basic.tookit.doamin.Result;
import com.due.bridge.tomcat.support.BasicController;
import com.due.cloud.module.customer.api.domain.request.SelectCustomerAccount;
import com.due.cloud.module.customer.api.domain.response.CustomerAccount;
import com.due.cloud.module.customer.entity.TbCustomerAccount;
import com.due.cloud.module.customer.service.ITbCustomerAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户账户表 前端控制器
 * </p>
 *
 * @author HanWenGang
 * @since 2024-01-29
 */
@RestController
@RequestMapping("/account")
@AllArgsConstructor
public class TbCustomerAccountController extends BasicController {

    private final ITbCustomerAccountService customerAccountService;

    @GetMapping("/one/condition")
    public Result<CustomerAccount> oneCondition(SelectCustomerAccount account) {
        return Result.exec(() -> this.copy(this.customerAccountService.selectData(this.copy(account, TbCustomerAccount.class)), CustomerAccount.class));
    }

}

