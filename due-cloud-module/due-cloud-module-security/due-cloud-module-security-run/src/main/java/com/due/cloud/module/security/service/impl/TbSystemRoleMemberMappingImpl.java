package com.due.cloud.module.security.service.impl;

import com.due.cloud.module.security.entity.TbSystemRoleMemberMapping;
import com.due.cloud.module.security.mapper.TbSystemRoleMemberMappingMapper;
import com.due.cloud.module.security.service.ITbSystemRoleMemberMappingService;
import com.due.cloud.bridge.mysql.service.imple.TableDataServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色用户映射 服务实现类
 * </p>
 *
 * @author HanWenGang
 * @since 2023-12-29
 */
@Service
public class TbSystemRoleMemberMappingImpl extends TableDataServiceImpl<TbSystemRoleMemberMappingMapper, TbSystemRoleMemberMapping> implements ITbSystemRoleMemberMappingService {

}
