package com.due.cloud.service.mobile.service.impl;

import com.due.basic.tookit.doamin.Result;
import com.due.cloud.service.mobile.integration.ICustomerIntegration;
import com.due.cloud.service.mobile.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

@Service
public class TestSertviceImpl implements TestService {

    @Autowired
    private ICustomerIntegration customerIntegration;

    @Autowired
    private Executor executor;

    @Override
    public String test01() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            Result<String> result = customerIntegration.test01();
            result.whenFailureThenThrowException();
            return result.getData();
        }, executor);
        try {
            return completableFuture.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
