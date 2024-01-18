package com.due.cloud.module.security.service.impl;

import com.due.basic.tookit.enums.ErrorEnum;
import com.due.basic.tookit.exception.LogicAssert;
import com.due.cloud.module.security.entity.TbSystemRole;
import com.due.cloud.module.security.mapper.TbSystemRoleMapper;
import com.due.cloud.module.security.service.ITbSystemRoleService;
import com.due.cloud.bridge.mysql.service.imple.TableDataServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author HanWenGang
 * @since 2023-12-29
 */
@Service
@Slf4j
public class TbSystemRoleImpl extends TableDataServiceImpl<TbSystemRoleMapper, TbSystemRole> implements ITbSystemRoleService {
    @Override
    public List<TbSystemRole> selectDataListByPath(String pathUrl) {
        return this.baseMapper.selectDataListByPath(pathUrl);
    }

    @Override
    public List<TbSystemRole> selectDataListByMemberId(Long memberId) {
        LogicAssert.isNull(memberId, ErrorEnum.PARAMETER_ERROR);
        return this.baseMapper.selectDataListByMemberId(memberId);
    }
}
