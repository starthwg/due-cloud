package com.due.cloud.module.customer.api.domain.response;

import com.due.basic.tookit.doamin.BasicDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class Customer extends BasicDomain<Customer> {

    private Long dataId;


    /**
     *  创建时间
     */
    private Date createTime;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 用户头像
     */
    private Long headImage;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 是否锁定：0-否，1-是
     */
    private Integer locked;

    /**
     * 登录用户名称
     */
    private String username;
}
