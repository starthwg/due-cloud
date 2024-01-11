package com.due.cloud.bridge.openfeign.config;

import com.due.cloud.bridge.openfeign.interceptor.FeignHeaderMoudleInterceptor;
import feign.Logger;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = {"com.due.cloud.rpc.**"})
@Configuration
public class BridgeFeignConfig {


    /**
     * Feign 日志级别
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }

    /**
     *  feign调用是将ThreadLocal在请求头中
     * @return
     */
    @Bean
    public FeignHeaderMoudleInterceptor feignHeaderMoudleInterceptor() {
        return new FeignHeaderMoudleInterceptor();
    }
}

