package com.due.cloud.bridge.openfeign.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.due.basic.tookit.constant.GlobalConstant;
import com.due.basic.tookit.doamin.DueRequest;
import com.due.basic.tookit.utils.ThreadContextStoreUtil;
import com.due.basic.tookit.utils.ThreadLocalUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;


/**
 *
 */
public class FeignHeaderMoudleInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        DueRequest dueRequest = ThreadLocalUtil.getDueRequest();
        if (null != dueRequest) {
            String dueRequestData = JSONObject.toJSONString(dueRequest);
            requestTemplate.header(GlobalConstant.DUE_RPC_MODULE_REQUEST, dueRequestData);
        }

    }

}
