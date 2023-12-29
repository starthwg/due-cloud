package com.due.cloud.module.security.service.impl;

import com.due.cloud.module.security.entity.TbSystemRole;
import com.due.cloud.module.security.mapper.TbSystemRoleMapper;
import com.due.cloud.module.security.service.ITbSystemRoleService;
import com.due.cloud.bridge.mysql.service.imple.TableDataServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author HanWenGang
 * @since 2023-12-29
 */
@Service
public class TbSystemRoleImpl extends TableDataServiceImpl<TbSystemRoleMapper, TbSystemRole> implements ITbSystemRoleService {

}
