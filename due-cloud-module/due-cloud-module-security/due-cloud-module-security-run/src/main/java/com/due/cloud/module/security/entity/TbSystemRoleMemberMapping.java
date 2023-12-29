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
 * 角色用户映射
 * </p>
 *
 * @author HanWenGang
 * @since 2023-12-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TbSystemRoleMemberMapping extends TableData<TbSystemRoleMemberMapping> {

    private static final long serialVersionUID = 1L;

    /**
     * 关联:tb_system_role.data_id
     */
    private Long roleId;

    /**
     * 关联:tb_system_member.data_id
     */
    private Long memberId;



}
