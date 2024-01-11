package com.due.basic.tookit.oauth.service.impl;

import com.due.basic.tookit.oauth.service.TokenEnhance;
import com.due.basic.tookit.oauth.user.DueBasicUser;

import java.util.UUID;

/**
 * UUID token增强器
 *
 * @author hanwengang
 */
public class UUIDTokenEnhance implements TokenEnhance {
    @Override
    public String enhance(DueBasicUser user) {
        return UUID.randomUUID().toString();
    }
}
