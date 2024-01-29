package com.due.cloud.service.auth.intergration.impl;

import com.due.basic.tookit.doamin.Result;
import com.due.basic.tookit.enums.ModuleCodeEnum;
import com.due.basic.tookit.enums.ModuleServiceScene;
import com.due.basic.tookit.enums.ServiceCodeEnum;
import com.due.basic.tookit.rpc.DueRpcInterceptor;
import com.due.basic.tookit.rpc.DueRpcService;
import com.due.cloud.module.customer.api.domain.request.SelectCustomerAccount;
import com.due.cloud.module.customer.api.domain.response.Customer;
import com.due.cloud.module.customer.api.domain.response.CustomerAccount;
import com.due.cloud.rpc.customer.service.ICustomerService;
import com.due.cloud.service.auth.intergration.ICustomerIntergration;

import javax.annotation.Resource;

@DueRpcService
@DueRpcInterceptor(request = ServiceCodeEnum.AUTH, response = ModuleCodeEnum.CUSTOMER)
public class CustomerIntergrationImpl implements ICustomerIntergration {

    @Resource
    private ICustomerService customerService;


    @DueRpcInterceptor(scene = ModuleServiceScene.SELECT)
    @Override
    public Result<CustomerAccount> getCustomerAccountCondition(SelectCustomerAccount condition) {
        return customerService.getCustomerAccount(condition);
    }

    @DueRpcInterceptor(scene = ModuleServiceScene.SELECT)
    @Override
    public Result<Customer> getCustomerByDataId(Long dataId) {
        return customerService.getCustomerByDataId(dataId);
    }
}
