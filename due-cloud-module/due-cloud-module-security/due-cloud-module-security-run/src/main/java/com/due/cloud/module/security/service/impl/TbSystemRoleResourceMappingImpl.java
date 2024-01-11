package com.due.cloud.module.security.service.impl;

import com.due.cloud.module.security.entity.TbSystemRoleResourceMapping;
import com.due.cloud.module.security.mapper.TbSystemRoleResourceMappingMapper;
import com.due.cloud.module.security.service.ITbSystemRoleResourceMappingService;
import com.due.cloud.bridge.mysql.service.imple.TableDataServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 资源角色映射 服务实现类
 * </p>
 *
 * @author HanWenGang
 * @since 2023-12-29
 */
@Service
public class TbSystemRoleResourceMappingImpl extends TableDataServiceImpl<TbSystemRoleResourceMappingMapper, TbSystemRoleResourceMapping> implements ITbSystemRoleResourceMappingService {

}
