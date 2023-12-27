package com.due.basic.tookit.utils;

import com.due.basic.tookit.enums.ErrorEnum;
import com.due.basic.tookit.exception.LogicAssert;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultJwtBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class JwtUtil {

    public static String encode(String secret, Map<String, Object> data, Integer expireTime) {
        JwtBuilder jwtBuilder = new DefaultJwtBuilder();
        LogicAssert.isEmpty(data, ErrorEnum.PARAMETER_ERROR);
        jwtBuilder.setClaims(data);
        jwtBuilder.signWith(SignatureAlgorithm.HS256, secret.getBytes(StandardCharsets.UTF_8));
        return jwtBuilder.compact();
    }


//    public static String getToken() {
//
//    }
}
