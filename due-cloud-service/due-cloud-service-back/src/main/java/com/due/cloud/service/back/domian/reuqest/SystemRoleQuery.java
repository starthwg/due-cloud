package com.due.cloud.service.back.domian.reuqest;

import com.due.basic.tookit.doamin.BasicDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SystemRoleQuery extends BasicDomain<SystemRoleQuery> {

    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;
}
