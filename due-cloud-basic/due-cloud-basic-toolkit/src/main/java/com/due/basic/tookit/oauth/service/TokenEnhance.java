package com.due.basic.tookit.oauth.service;

import com.due.basic.tookit.oauth.user.DueBasicUser;


/**
 *  token增强器
 */
public interface TokenEnhance {

    String enhance(DueBasicUser user);
}
