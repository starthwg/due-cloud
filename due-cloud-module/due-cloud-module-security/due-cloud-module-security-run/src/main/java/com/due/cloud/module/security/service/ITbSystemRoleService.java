package com.due.cloud.module.security.service;

import com.due.cloud.module.security.entity.TbSystemRole;
import com.due.cloud.bridge.mysql.service.ITableDataService;

import java.util.List;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author HanWenGang
 * @since 2023-12-29
 */
public interface ITbSystemRoleService extends ITableDataService<TbSystemRole> {

    /**
     *  通过请求路径获取对应的资源
     * @param pathUrl String类型的url
     * @return List类型
     */
    List<TbSystemRole> selectDataListByPath(String pathUrl);

    /**
     *  通过用户ID获取用户对应的角色信息
     * @param memberId Long类型的用户Id
     * @return list类型的角色列表
     */
    List<TbSystemRole> selectDataListByMemberId(Long memberId);

    /**
     *  通过条件查询系统角色信息
     * @param condition TbSystemRole 类型的条件
     * @return List类型
     */
    List<TbSystemRole> selectDataList(TbSystemRole condition);
}
