package com.due.basic.setting.config;

import com.due.basic.tookit.oauth.service.DueTokenService;
import com.due.basic.tookit.oauth.service.impl.DueTokenServiceImpl;
import com.due.basic.tookit.oauth.service.impl.UUIDTokenEnhance;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalConfig {


//    @Bean
////    @ConditionalOnMissingBean
//    public DueTokenService dueTokenService() {
//        UUIDTokenEnhance uuidTokenEnhance = new UUIDTokenEnhance();
//        DueTokenServiceImpl dueTokenService = new DueTokenServiceImpl();
//        dueTokenService.setUuidTokenEnhance(uuidTokenEnhance);
//        return dueTokenService;
//    }

}
