package com.due.cloud.module.customer.entity;

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
 * 用户账户表
 * </p>
 *
 * @author HanWenGang
 * @since 2024-01-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TbCustomerAccount extends TableData<TbCustomerAccount> {

    private static final long serialVersionUID = 1L;

    /**
     * 类型：1-用户名称 2-微信小程序 3.其他
     */
    private Integer type;

    /**
     * 凭证
     */
    private String secret;

    /**
     * 关联：due_customer.tb_customer.data_id
     */
    private Long customerId;

}
