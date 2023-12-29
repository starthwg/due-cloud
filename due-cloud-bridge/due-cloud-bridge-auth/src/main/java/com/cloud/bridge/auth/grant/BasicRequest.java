package com.cloud.bridge.auth.grant;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 封装获取token的请求
 * @author hanwengang
 */
public abstract class BasicRequest {


    /**
     * 请求参数
     */
    private Map<String, Object> params;

    /**
     * 请求
     */
    private HttpServletRequest request;

    public BasicRequest(Map<String, Object> params, HttpServletRequest request) {
        this.params = params;
        this.request = request;
    }

    public BasicRequest() {
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
