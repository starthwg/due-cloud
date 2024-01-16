package com.due.cloud.bridge.okhttp.config;


import lombok.Data;

@Data
public class HttpClientAutoProxyConfig {

    private boolean proxyEnable = false;
    private boolean credentialEnable = false;


    /**
     *  代理主机
     */
    private String proxyHost;


    /**
     *  代码端口
     */
    private int proxyPort;

    /**
     *  用户名
     */
    private String username;


    /**
     *  密码
     */
    private String password;
}
