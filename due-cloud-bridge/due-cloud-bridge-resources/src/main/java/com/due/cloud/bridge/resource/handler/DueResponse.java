package com.due.cloud.bridge.resource.handler;

import com.due.basic.tookit.doamin.Result;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface DueResponse {
    default void responseError(HttpServletResponse response,Result<?> result, int status) {
        setResponseJSON(response, status);
        try {
            writeValue(response, result);
        } catch (IOException e) {
            System.out.println(e);
        }
    }


    default void setResponseJSON(HttpServletResponse response, int status) {
        if (null == response) return;
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(status);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
    }


    default void writeValue(HttpServletResponse response, Result<?> result) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        //允许使用未带引号的字段名
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        //允许使用单引号
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.writeValue(response.getOutputStream(), result);
    }
}
