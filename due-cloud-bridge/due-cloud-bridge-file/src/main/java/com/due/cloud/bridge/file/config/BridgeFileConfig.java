package com.due.cloud.bridge.file.config;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.due.cloud.bridge.file.support.DelegateDueFileTemplate;
import com.due.cloud.bridge.file.support.impl.MinioFileTemplate;
import com.due.cloud.bridge.okhttp.config.HttpClientAutoProxyConfig;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Slf4j
@Configuration
@EnableConfigurationProperties(value = {BridgeFileProperties.class})
public class BridgeFileConfig {


    @Configuration
    public static class FileClientConfig {
        @Autowired
        private BridgeFileProperties bridgeFileProperties;

        @Autowired
        private OkHttpClient okHttpClient;

        @Autowired
        private HttpClientAutoProxyConfig httpClientAutoProxyConfig;

        /**
         * Min-Io客服端
         *
         * @return MinioClient
         */

        @Bean
        @ConditionalOnProperty(prefix = "due.file.min-io.enable", value = "true")
        public MinioClient minIoClient() {
            BridgeFileProperties.MinIo minIo = bridgeFileProperties.getMinIo();

            return MinioClient.builder().httpClient(okHttpClient).credentials(minIo.getAccessKey(), minIo.getSecretKey())
                    .endpoint(minIo.getAddress(), minIo.getPort(), false).build();
        }

        /**
         * OSS-Aili客户端的配置
         *
         * @return OSSClient
         */
        @ConditionalOnProperty(prefix = "due.file.oss-ai-li.enable", value = "true")
        @Bean
        public OSSClient ailiOssClient() {
            BridgeFileProperties.OssAiLi ossAiLi = bridgeFileProperties.getOssAiLi();
            CredentialsProvider credentialsProvider = new DefaultCredentialProvider(ossAiLi.getAccessKey(), ossAiLi.getSecretKey());
            ClientConfiguration clientConfiguration = new ClientConfiguration();
            if (httpClientAutoProxyConfig.isProxyEnable()) {
                clientConfiguration.setProxyHost(httpClientAutoProxyConfig.getProxyHost());
                clientConfiguration.setProxyPort(httpClientAutoProxyConfig.getProxyPort());
                if (httpClientAutoProxyConfig.isCredentialEnable()) {
                    clientConfiguration.setProxyUsername(httpClientAutoProxyConfig.getUsername());
                    clientConfiguration.setProxyPassword(httpClientAutoProxyConfig.getPassword());
                }
            }
            return new OSSClient(ossAiLi.getEndpoint(), credentialsProvider, clientConfiguration);
        }

    }

    /**
     * 文件模板配置
     */
    @Configuration
    @Import(value = {DelegateDueFileTemplate.class})
    public static class FileTemplateConfig {

        @Bean
        @ConditionalOnBean(value = {MinioClient.class})
        public MinioFileTemplate minioFileTemplate(MinioClient minioClient) {
            MinioFileTemplate minioFileTemplate = new MinioFileTemplate();
            minioFileTemplate.setClient(minioClient);
            return minioFileTemplate;
        }
    }

}
