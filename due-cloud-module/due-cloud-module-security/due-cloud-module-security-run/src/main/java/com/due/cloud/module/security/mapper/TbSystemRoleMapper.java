package com.due.cloud.module.security.mapper;

import com.due.cloud.module.security.entity.TbSystemRole;
import com.due.cloud.bridge.mysql.mapper.ITableDataMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author HanWenGang
 * @since 2023-12-29
 */
public interface TbSystemRoleMapper extends ITableDataMapper<TbSystemRole> {

    List<TbSystemRole> selectDataListByPath(@Param("pathUrl") String pathUrl);

    List<TbSystemRole> selectDataListByMemberId(@Param("memberId") Long memberId);
}
