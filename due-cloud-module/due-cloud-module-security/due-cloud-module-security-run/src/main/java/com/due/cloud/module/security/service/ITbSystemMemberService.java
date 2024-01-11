package com.due.cloud.module.security.service;

import com.due.cloud.module.security.entity.TbSystemMember;
import com.due.cloud.bridge.mysql.service.ITableDataService;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author HanWenGang
 * @since 2023-12-29
 */
public interface ITbSystemMemberService extends ITableDataService<TbSystemMember> {

    TbSystemMember selectSystemMemberByUsername(String userName);
}
