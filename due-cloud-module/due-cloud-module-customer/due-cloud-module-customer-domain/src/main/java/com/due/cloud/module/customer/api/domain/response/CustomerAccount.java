package com.due.cloud.module.customer.api.domain.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerAccount extends Customer {

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
