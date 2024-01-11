package com.due.cloud.module.security.entity;

import com.due.cloud.bridge.mysql.domian.TableData;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统用户
 * </p>
 *
 * @author HanWenGang
 * @since 2023-12-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TbSystemMember extends TableData<TbSystemMember> {

    private static final long serialVersionUID = 1L;

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
