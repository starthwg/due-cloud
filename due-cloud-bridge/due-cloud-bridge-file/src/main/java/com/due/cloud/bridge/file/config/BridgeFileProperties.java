package com.due.cloud.bridge.file.config;

import com.due.basic.tookit.doamin.BasicDomain;
import com.due.cloud.bridge.file.support.FileClientEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "due.file")
@EqualsAndHashCode(callSuper = true)
public class BridgeFileProperties extends BasicDomain<BridgeFileProperties>{


    /**
     *  首选文件存储客服端
     */
    private FileClientEnum fileClient;

    /**
     *  MinIo的配置
     */
    private MinIo minIo;


    /**
     *  AiliOss
     */
    private OssAiLi ossAiLi;


    @Data
    @EqualsAndHashCode(callSuper = true)
    static class MinIo extends BasicDomain<MinIo> {

        /**
         *  是否开启
         */
        private boolean enable = false;


        /**
         * ip地址
         */
        private String address;


        /**
         * 端口号
         */
        private int port;


        /**
         *  桶
         */
        private String bucketName;


        /**
         *  连接密码
         */
        private String accessKey;


        /**
         *  秘钥
         */
        private String secretKey;
    }


    /**
     *  oss Aili的配置
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    static class OssAiLi extends BasicDomain<OssAiLi> {


        /**
         *  是否开启
         */
        private boolean enable = false;


        /**
         *  桶
         */
        private String bucketName;


        /**
         *  连接密码
         */
        private String accessKey;


        /**
         *  秘钥
         */
        private String secretKey;

        /**
         *  地址
         */
        private String endpoint;
    }
}
