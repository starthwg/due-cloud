package com.due.cloud.bridge.openfeign.interceptor;

import com.alibaba.fastjson2.JSONObject;
import com.due.basic.tookit.constant.GlobalConstant;
import com.due.basic.tookit.doamin.DueRequest;
import com.due.basic.tookit.utils.ThreadLocalUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 *
 */
@Slf4j
public class FeignHeaderMoudleInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        DueRequest dueRequest = ThreadLocalUtil.getRpcDueRequest();
        try {
            if (null != dueRequest) {
                String dueRequestData = JSONObject.toJSONString(dueRequest);
                String encode = URLEncoder.encode(dueRequestData, "UTF-8");
                // 设置把上游的thread传递给下游的
                requestTemplate.header(GlobalConstant.DUE_RPC_MODULE_REQUEST, encode);
            }
        } catch (UnsupportedEncodingException e) {
            log.info(ExceptionUtils.getStackTrace(e));
        }
    }

}
