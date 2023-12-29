package com.due.cloud.module.security.domain.response;

import com.due.basic.tookit.doamin.BasicDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class SystemMember extends BasicDomain<SystemMember> {

    private Long dataId;


    private Date createTime;


    private Integer dataStatus;


    /**
     * 账户名
     */
    private String username;

    /**
     * 账户口令
     */
    private String password;

    /**
     * 是否锁定：0-否，1-是
     */
    private Integer locked;

    /**
     * 关联头像ID
     */
    private Long head;

    /**
     * 名称
     */
    private String name;

    /**
     * 手机号码
     */
    private String mobile;


}
