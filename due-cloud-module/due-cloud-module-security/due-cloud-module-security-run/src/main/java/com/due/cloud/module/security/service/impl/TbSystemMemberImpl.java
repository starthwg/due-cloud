package com.due.cloud.module.security.service.impl;

import com.due.basic.tookit.enums.ErrorEnum;
import com.due.basic.tookit.exception.LogicAssert;
import com.due.basic.tookit.utils.LogicUtil;
import com.due.cloud.module.security.entity.TbSystemMember;
import com.due.cloud.module.security.mapper.TbSystemMemberMapper;
import com.due.cloud.module.security.service.ITbSystemMemberService;
import com.due.cloud.bridge.mysql.service.imple.TableDataServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author HanWenGang
 * @since 2023-12-29
 */
@Service
public class TbSystemMemberImpl extends TableDataServiceImpl<TbSystemMemberMapper, TbSystemMember> implements ITbSystemMemberService {
    @Override
    public TbSystemMember selectSystemMemberByUsername(String userName) {
        LogicAssert.isBlank(userName, ErrorEnum.PARAMETER_ERROR);
        return this.lambdaQuery().eq(TbSystemMember::getUsername, userName).last("LIMIT 1").one();
    }
}
