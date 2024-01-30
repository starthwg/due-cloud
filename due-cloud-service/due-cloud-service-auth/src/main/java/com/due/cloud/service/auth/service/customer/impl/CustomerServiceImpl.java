package com.due.cloud.service.auth.service.customer.impl;

import com.due.basic.tookit.doamin.Result;
import com.due.cloud.module.customer.api.domain.request.SelectCustomerAccount;
import com.due.cloud.module.customer.api.domain.response.Customer;
import com.due.cloud.module.customer.api.domain.response.CustomerAccount;
import com.due.cloud.service.auth.intergration.ICustomerIntergration;
import com.due.cloud.service.auth.service.customer.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {


    private final ICustomerIntergration customerIntergration;


    @Override
    public CustomerAccount getCustomerAccountCondition(SelectCustomerAccount condition) {
        Result<CustomerAccount> accountCondition = customerIntergration.getCustomerAccountCondition(condition);
        accountCondition.whenFailureThenThrowException();
        return accountCondition.getData();
    }

    @Override
    public Customer getCustomerByDataId(Long dataId) {
        Result<Customer> result = customerIntergration.getCustomerByDataId(dataId);
        result.whenFailureThenThrowException();
        return result.getData();
    }
}
