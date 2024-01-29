package com.due.cloud.module.customer.entity;

import com.due.cloud.bridge.mysql.domian.TableData;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author HanWenGang
 * @since 2024-01-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TbCustomer extends TableData<TbCustomer> {

    private static final long serialVersionUID = 1L;

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
