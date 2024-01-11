package com.due.basic.tookit.utils;

import com.alibaba.fastjson.JSONObject;
import com.due.basic.tookit.constant.GlobalConstant;
import com.due.basic.tookit.constant.GlobalThreadLocalConstant;
import com.due.basic.tookit.doamin.DueRequest;
import com.due.basic.tookit.enums.ChannelEnum;
import com.due.basic.tookit.enums.ModuleCodeEnum;
import com.due.basic.tookit.enums.ModuleServiceScene;
import com.due.basic.tookit.enums.ServiceCodeEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Optional;

@Slf4j
public class ThreadLocalUtil {


    public static void setDueRequest(DueRequest dueRequest) {
        if (null == dueRequest) {
            return;
        }
        Optional.ofNullable(dueRequest.getServiceRequestCode()).ifPresent(e -> ThreadContextStoreUtil.getInstance().set(GlobalThreadLocalConstant.SERVICE_REQUEST_CODE, e));
        Optional.ofNullable(dueRequest.getModuleResponseCode()).ifPresent(e -> ThreadContextStoreUtil.getInstance().set(GlobalThreadLocalConstant.MODULE_RESPONSE_CODE, e));
        Optional.ofNullable(dueRequest.getChannelEnum()).ifPresent(e -> ThreadContextStoreUtil.getInstance().set(GlobalThreadLocalConstant.CHANNEL_ENUM, e));
        Optional.ofNullable(dueRequest.getServiceScene()).ifPresent(e -> ThreadContextStoreUtil.getInstance().set(GlobalThreadLocalConstant.SERVICE_SCENE, e));
        Optional.ofNullable(dueRequest.getMemberId()).ifPresent(e -> ThreadContextStoreUtil.getInstance().set(GlobalThreadLocalConstant.MEMBER_ID, e));
        Optional.ofNullable(dueRequest.getRpcTime()).ifPresent(e -> ThreadContextStoreUtil.getInstance().set(GlobalThreadLocalConstant.RPC_TIME, e.getTime()));
        Optional.ofNullable(dueRequest.getSerialNo()).ifPresent(e -> ThreadContextStoreUtil.getInstance().set(GlobalThreadLocalConstant.SERIAL_NO, e));
    }


    public static void setDueRequest(String json) {
        if (LogicUtil.isAllNotBlank(json)) {
            try {
                DueRequest dueRequest = JSONObject.parseObject(json, DueRequest.class);
                setDueRequest(dueRequest);
                set(GlobalConstant.DUE_RPC_MODULE_REQUEST, dueRequest);
            } catch (Exception e) {
                log.error("解释 dueRequest失败：{}", e.getMessage());
            }
        }
    }

    public static void removeDueRequest() {
        ThreadContextStoreUtil.getInstance().removeKey(GlobalThreadLocalConstant.SERIAL_NO);
        ThreadContextStoreUtil.getInstance().removeKey(GlobalThreadLocalConstant.SERVICE_REQUEST_CODE);
        ThreadContextStoreUtil.getInstance().removeKey(GlobalThreadLocalConstant.MODULE_RESPONSE_CODE);
        ThreadContextStoreUtil.getInstance().removeKey(GlobalThreadLocalConstant.MEMBER_ID);
        ThreadContextStoreUtil.getInstance().removeKey(GlobalThreadLocalConstant.RPC_TIME);
        ThreadContextStoreUtil.getInstance().removeKey(GlobalThreadLocalConstant.SERVICE_SCENE);
        ThreadContextStoreUtil.getInstance().removeKey(GlobalThreadLocalConstant.CHANNEL_ENUM);

        ThreadContextStoreUtil.getInstance().removeKey(GlobalConstant.DUE_RPC_MODULE_REQUEST);
    }

    public static void removeDueRequestRpc() {
        ThreadContextStoreUtil.getInstance().removeKey(GlobalThreadLocalConstant.SERVICE_REQUEST_CODE);
        ThreadContextStoreUtil.getInstance().removeKey(GlobalThreadLocalConstant.MODULE_RESPONSE_CODE);
        ThreadContextStoreUtil.getInstance().removeKey(GlobalThreadLocalConstant.RPC_TIME);
        ThreadContextStoreUtil.getInstance().removeKey(GlobalThreadLocalConstant.SERVICE_SCENE);
        ThreadContextStoreUtil.getInstance().removeKey(GlobalThreadLocalConstant.CHANNEL_ENUM);

        ThreadContextStoreUtil.getInstance().removeKey(GlobalConstant.DUE_RPC_MODULE_REQUEST);
    }

    /**
     * 获取用户ID
     *
     * @return Long类型
     */
    public static Long getMemberId() {
        return Optional.ofNullable(ThreadContextStoreUtil.getInstance().get(GlobalThreadLocalConstant.MEMBER_ID)).map(e -> {
            if (e instanceof Long) {
                return (Long) e;
            } else {

                return Long.parseLong(e.toString());
            }
        }).orElse(null);
    }

    public static ChannelEnum getChannel() {
        return (ChannelEnum) Optional.ofNullable(ThreadContextStoreUtil.getInstance().get(GlobalThreadLocalConstant.CHANNEL_ENUM)).filter(e -> e instanceof ChannelEnum).orElse(null);
    }

    public static String getSerialNo() {
        return Optional.ofNullable(ThreadContextStoreUtil.getInstance().get(GlobalThreadLocalConstant.SERIAL_NO)).filter(e -> e instanceof String).map(Object::toString).orElse(null);
    }

    public static ModuleServiceScene getServiceCode() {
        return (ModuleServiceScene) ThreadContextStoreUtil.getInstance().get(GlobalThreadLocalConstant.SERVICE_SCENE);
    }

    public static ModuleCodeEnum getResponseModule() {
        return (ModuleCodeEnum) ThreadContextStoreUtil.getInstance().get(GlobalThreadLocalConstant.MODULE_RESPONSE_CODE);
    }

    public static ServiceCodeEnum getRequestService() {
        return (ServiceCodeEnum) ThreadContextStoreUtil.getInstance().get(GlobalThreadLocalConstant.SERVICE_REQUEST_CODE);
    }
    public static void set(String key, Object value) {
        ThreadContextStoreUtil.getInstance().set(key, value);
    }

    public static DueRequest getDueRequest() {
       return   DueRequest.of().setSerialNo(ThreadLocalUtil.getSerialNo()).setChannelEnum(ThreadLocalUtil.getChannel())
                .setRpcTime(new Date((Long) ThreadContextStoreUtil.getInstance().get(GlobalThreadLocalConstant.RPC_TIME)))
                .setMemberId(ThreadLocalUtil.getMemberId()).setServiceScene(Optional.ofNullable(ThreadContextStoreUtil.getInstance().get(GlobalThreadLocalConstant.SERVICE_SCENE)).map(e -> (ModuleServiceScene) e).orElse(null))
                .setServiceRequestCode( (ServiceCodeEnum) ThreadContextStoreUtil.getInstance().get(GlobalThreadLocalConstant.SERVICE_REQUEST_CODE))
                .setModuleResponseCode((ModuleCodeEnum) ThreadContextStoreUtil.getInstance().get(GlobalThreadLocalConstant.MODULE_RESPONSE_CODE));
    }
}
