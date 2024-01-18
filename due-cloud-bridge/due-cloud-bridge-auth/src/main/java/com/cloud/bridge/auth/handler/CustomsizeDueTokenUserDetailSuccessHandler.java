package com.cloud.bridge.auth.handler;

import com.due.basic.tookit.enums.ErrorEnum;
import com.due.basic.tookit.exception.LogicAssert;
import com.due.basic.tookit.oauth.Token;
import com.due.basic.tookit.oauth.user.DueBasicUser;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CustomsizeDueTokenUserDetailSuccessHandler implements DueTokenUserDetailSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, DueBasicUser dueBasicUser) throws IOException {
        log.info("认证成功的用户:{}", dueBasicUser);
        if (null != dueBasicUser) {
            Map<String, Object> data = new HashMap<>();
            data.put("memberId", dueBasicUser.getMemberId());
            data.put("authorities", dueBasicUser.getAuthorities());
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json;charset=UTF-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Cache-Control", "no-cache");
            ObjectMapper mapper = new ObjectMapper();
            //允许使用未带引号的字段名
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            //允许使用单引号
            mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            mapper.writeValue(response.getOutputStream(), data);
        }

    }
}
