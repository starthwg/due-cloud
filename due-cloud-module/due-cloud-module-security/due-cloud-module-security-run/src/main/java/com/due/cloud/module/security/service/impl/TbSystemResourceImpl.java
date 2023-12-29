package com.due.cloud.module.security.service.impl;

import com.due.cloud.module.security.entity.TbSystemResource;
import com.due.cloud.module.security.mapper.TbSystemResourceMapper;
import com.due.cloud.module.security.service.ITbSystemResourceService;
import com.due.cloud.bridge.mysql.service.imple.TableDataServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 资源 服务实现类
 * </p>
 *
 * @author HanWenGang
 * @since 2023-12-29
 */
@Service
public class TbSystemResourceImpl extends TableDataServiceImpl<TbSystemResourceMapper, TbSystemResource> implements ITbSystemResourceService {

}
