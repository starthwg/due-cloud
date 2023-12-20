package com.due.bridge.tomcat.support;

import com.due.basic.tookit.doamin.Domain;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 *  未知异常通知对象
 */
@Data
@Accessors(chain = true)
public class UnknownExecoptionNotice implements Domain {

    private static final long serialVersionUID = 42L;

    /**
     *  服务所在的ip地址
     */
    private String ip;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     *  异常信息
     */
    private Exception exception;


    public static UnknownExecoptionNotice of() {
        return new UnknownExecoptionNotice();
    }
}
