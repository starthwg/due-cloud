package com.due.cloud.service.back.domian.response;

import com.due.basic.tookit.doamin.BasicDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class SystemRoleVo extends BasicDomain<SystemRoleVo> {

    private Long dataId;

    private Date createTime;

    private Integer dataStatus;


    /**
     * 角色编码
     */
    private String code;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;



}
