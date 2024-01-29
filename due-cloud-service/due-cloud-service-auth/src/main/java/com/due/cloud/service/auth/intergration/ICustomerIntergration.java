package com.due.cloud.service.auth.intergration;

import com.due.basic.tookit.doamin.Result;
import com.due.cloud.module.customer.api.domain.request.SelectCustomerAccount;
import com.due.cloud.module.customer.api.domain.response.Customer;
import com.due.cloud.module.customer.api.domain.response.CustomerAccount;

public interface ICustomerIntergration {

    Result<CustomerAccount> getCustomerAccountCondition(SelectCustomerAccount condition);


    Result<Customer> getCustomerByDataId(Long dataId);
}
