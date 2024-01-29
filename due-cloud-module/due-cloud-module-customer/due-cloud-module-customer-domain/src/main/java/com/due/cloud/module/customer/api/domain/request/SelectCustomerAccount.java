package com.due.cloud.module.customer.api.domain.request;

import com.due.basic.tookit.doamin.BasicDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SelectCustomerAccount extends BasicDomain<SelectCustomerAccount> {


    /**
     * openID
     */
    private String openId;

    /**
     * 账户类型
     */
    private Integer type;


    /**
     * 手机号码
     */
    private String mobile;


    public static SelectCustomerAccount of() {
        return new SelectCustomerAccount();
    }
}
