package com.due.basic.tookit.utils;

import com.due.basic.tookit.oauth.user.DueBasicUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtUtil {

    public static String encode(String key, Map<String, Object> data, Long expired) {
        JwtBuilder builder = Jwts.builder();
        if (data != null && !data.isEmpty()) {
            builder.setClaims(data);
        }
        Calendar calendar = Calendar.getInstance();
        builder.setIssuedAt(calendar.getTime());
        calendar.setTimeInMillis(expired);
        builder.setExpiration(calendar.getTime());
        builder.signWith(SignatureAlgorithm.HS256, key.getBytes(StandardCharsets.UTF_8));
        return builder.compact();
    }

    public static String encode(String secret, Long expireTime, DueBasicUser basicUser) {
        if (null == basicUser) return null;
        Map<String, Object> map = new HashMap<>(2);
        map.put("dataId", basicUser.getMemberId());
        map.put("authorities", basicUser.getAuthorities());
        return encode(secret, map, expireTime);
    }

    public static Map<String, Object> decode(String key, String encodedString) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(key.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(encodedString);
            if (claims == null) return null;
            return claims.getBody();
        } catch (Exception e) {
            log.error("解密JWT Token数据失败", e);
            return null;
        }
    }

    public static boolean expired(String key, String encodedString) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(key.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(encodedString);
            if (claims == null) return true;
            Date date = claims.getBody().getExpiration();
            return date == null || date.before(DateUtil.getDate());
        } catch (Exception e) {
            log.error("解密JWT Token数据失败", e);
            return true;
        }
    }
}