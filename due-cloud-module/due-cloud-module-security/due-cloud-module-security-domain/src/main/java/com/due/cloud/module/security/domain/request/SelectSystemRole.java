package com.due.cloud.module.security.domain.request;

import com.due.basic.tookit.doamin.BasicDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SelectSystemRole extends BasicDomain<SelectSystemRole> {

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;


    /**
     *  请求路径
     */
    private String pathUrl;
}
