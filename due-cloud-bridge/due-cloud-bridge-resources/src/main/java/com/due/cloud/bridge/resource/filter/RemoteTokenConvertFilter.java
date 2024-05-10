package com.due.cloud.bridge.resource.filter;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.due.basic.tookit.constant.GlobalAuthConstant;
import com.due.basic.tookit.doamin.Result;
import com.due.basic.tookit.utils.HttpUtil;
import com.due.basic.tookit.utils.LogicUtil;
import com.due.cloud.bridge.resource.MobileAuthenticated;
import com.due.cloud.bridge.resource.config.DueResourcesProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Component
@ConditionalOnMissingBean(value = {DueTokenConvertUserDetailFilter.class})
public class RemoteTokenConvertFilter extends DueTokenConvertUserDetailFilter {


    @Autowired
    private DueResourcesProperties dueResourcesProperties;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Authentication tokenConvertAuthentication(String token) {
        DueResourcesProperties.Auth auth = dueResourcesProperties.getAuth();
        if (null == auth) return null;
        JSONObject header = new JSONObject();
        try {
            header.put(GlobalAuthConstant.Authorization, GlobalAuthConstant.BEARER + token);
//        header.put()
            Result<String> stringResult = HttpUtil.auth(restTemplate, auth.getAuthAddress(), header, null, false);
            String data = stringResult.getData();
            JSONObject jsonObject = JSONObject.parseObject(data);
            MobileAuthenticated mobileAuthenticated = new MobileAuthenticated();
            mobileAuthenticated.setAuthenticated(true);
            mobileAuthenticated.setMemberId(jsonObject.getLong("memberId"));
            String string = jsonObject.getString("authorities");
            if (LogicUtil.isAllBlank(string)) {
                JSONArray objects = JSON.parseArray(string);
                List<SimpleGrantedAuthority> roleSet = objects.stream().map(e -> new SimpleGrantedAuthority(e.toString())).collect(Collectors.toList());
                mobileAuthenticated.setAuthorities(roleSet);
            }
            return mobileAuthenticated;
        } catch (Exception e) {
            log.error("token认证错误：{}", e.getMessage());
            return null;
        }
    }
}
