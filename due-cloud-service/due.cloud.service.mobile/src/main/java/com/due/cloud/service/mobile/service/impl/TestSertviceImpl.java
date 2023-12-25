package com.due.cloud.service.mobile.service.impl;

import com.due.basic.tookit.doamin.Result;
import com.due.cloud.service.mobile.integration.ICustomerIntegration;
import com.due.cloud.service.mobile.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestSertviceImpl implements TestService {

    @Autowired
    private ICustomerIntegration customerIntegration;

    @Override
    public String test01() {
        Result<String> result = customerIntegration.test01();
        result.whenFailureThenThrowException();
        return result.getData();
    }
}
