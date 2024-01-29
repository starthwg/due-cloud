package com.due.cloud.service.auth.service.customer;

import com.due.cloud.module.customer.api.domain.request.SelectCustomerAccount;
import com.due.cloud.module.customer.api.domain.response.Customer;
import com.due.cloud.module.customer.api.domain.response.CustomerAccount;

public interface ICustomerService {


    CustomerAccount getCustomerAccountCondition(SelectCustomerAccount condition);


    Customer getCustomerByDataId(Long dataId);
}
