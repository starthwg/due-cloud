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
 * 资源
 * </p>
 *
 * @author HanWenGang
 * @since 2023-12-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TbSystemResource extends TableData<TbSystemResource> {

    private static final long serialVersionUID = 1L;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 图标
     */
    private String icon;

    /**
     * 编码
     */
    private String code;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 路径
     */
    private String patch;

    /**
     * 父级ID
     */
    private Long parentId;

    /**
     * 状态：0-禁用 1-启用
     */
    private Integer status;



}
