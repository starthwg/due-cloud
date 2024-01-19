package com.due.cloud.bridge.openfeign.config;

import com.due.cloud.bridge.openfeign.interceptor.FeignHeaderMoudleInterceptor;
import feign.Feign;
import feign.Logger;
import feign.Retryer;
import feign.querymap.BeanQueryMapEncoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = {"com.due.cloud.rpc.**"}, defaultConfiguration = BridgeFeignConfig.class)
@Configuration
public class BridgeFeignConfig {


    /**
     * Feign 日志级别
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    /**
     *  feign调用是将ThreadLocal在请求头中
     * @return
     */
    @Bean
    public FeignHeaderMoudleInterceptor feignHeaderMoudleInterceptor() {
        return new FeignHeaderMoudleInterceptor();
    }


    /**
     *  配置feign
     */
    @Bean
    public Feign.Builder feignBuilder() {
        return Feign.builder()
                // 将对象转化map类型
                .queryMapEncoder(new BeanQueryMapEncoder())
                // 重试策略
                .retryer(Retryer.NEVER_RETRY);
    }
}

