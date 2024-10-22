package com.due.basic.tookit.oauth;

import com.due.basic.tookit.doamin.Domain;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * due项目的token对象
 *
 * @author hanwengang
 */
@Data
public class Token implements Domain {

    /**
     * 访问token
     */
    private String accessToken;

    /**
     * token传递类型
     */
    private final String tokenType = "bearer";

    /**
     * 有效期
     */
    private Long expiration;

}
