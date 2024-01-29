package com.due.cloud.rpc.customer.service;

import com.due.basic.tookit.doamin.Result;
import com.due.cloud.module.customer.api.domain.request.SelectCustomerAccount;
import com.due.cloud.module.customer.api.domain.response.Customer;
import com.due.cloud.module.customer.api.domain.response.CustomerAccount;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * customer服务
 */
@FeignClient(name = "ModuleCustomer")
public interface ICustomerService {

    /**
     * 通过添加获取账户信息
     *
     * @param condition SelectCustomerAccount 类型
     * @return CustomerAccount 类型
     */
    @GetMapping("/customer/account/one/condition")
    Result<CustomerAccount> getCustomerAccount(@SpringQueryMap SelectCustomerAccount condition);


    /**
     *  通过数据主键获取customerId
     * @param dataId Long类型
     * @return Customer 类型
     */
    @GetMapping("/customer/customer/one/{dataId}")
    Result<Customer> getCustomerByDataId(@PathVariable("dataId") Long dataId);
}
