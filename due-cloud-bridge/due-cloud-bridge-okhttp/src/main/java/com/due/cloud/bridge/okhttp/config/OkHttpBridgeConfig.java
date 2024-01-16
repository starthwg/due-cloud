package com.due.cloud.bridge.okhttp.config;

import com.due.cloud.bridge.okhttp.OkHttpClientAutoProxyBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import okhttp3.OkHttpClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

/**
 * @author hanwengang
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ConfigurationProperties(prefix = "due.http")
@Configuration
public class OkHttpBridgeConfig extends HttpClientAutoProxyConfig{

    @Bean
    public RestTemplate okHttpRestTemplate() {
//		System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");
        OkHttp3ClientHttpRequestFactory requestFactory = new OkHttp3ClientHttpRequestFactory(this.okHttpClient());
        requestFactory.setConnectTimeout(100000); // 连接超时时间，毫秒
        requestFactory.setReadTimeout(100000); // 读写超时时间，毫秒
        requestFactory.setWriteTimeout(100000); // 读写超时时间，毫秒
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }

    @Bean
    public OkHttpClient okHttpClient() {
        OkHttpClientAutoProxyBuilder okHttpClientAutoProxyBuilder = new OkHttpClientAutoProxyBuilder(this);
        OkHttpClient build = okHttpClientAutoProxyBuilder.build();
        return build;
    }

}
