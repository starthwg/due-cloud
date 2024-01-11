package com.cloud.bridge.auth.convert;

/**
 * token转化成用户信息
 *
 * @author hanwengang
 */
public interface RequestTokenConvert {


    /**
     * 是否支持转化
     *
     * @param code 编码
     * @return ture 支持 false 不支持
     */
    boolean support(String code);
}
