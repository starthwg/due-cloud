package com.due.cloud.service.mobile.integration.impl;

import com.due.basic.tookit.doamin.Result;
import com.due.basic.tookit.enums.ModuleCodeEnum;
import com.due.basic.tookit.enums.ModuleServiceScene;
import com.due.basic.tookit.enums.ServiceCodeEnum;
import com.due.basic.tookit.rpc.RpcInterceptor;
import com.due.cloud.rpc.customer.service.ICustomerService;
import com.due.cloud.service.mobile.integration.ICustomerIntegration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;


@RpcInterceptor(request = ServiceCodeEnum.MOBILE, response = ModuleCodeEnum.CUSTOMER)
@Repository
@Slf4j
public class CustomerIntegrationImpl implements ICustomerIntegration {


    @Resource
    private ICustomerService customerService;

    @RpcInterceptor(scene = ModuleServiceScene.SELECT)
    @Override
    public Result<String> test01() {
        log.info("1111");
        return customerService.test01();
    }
}
