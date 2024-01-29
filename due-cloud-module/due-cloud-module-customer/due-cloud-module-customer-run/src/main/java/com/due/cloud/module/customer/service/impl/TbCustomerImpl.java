package com.due.cloud.module.customer.service.impl;

import com.due.basic.tookit.enums.ErrorEnum;
import com.due.basic.tookit.exception.LogicAssert;
import com.due.cloud.module.customer.entity.TbCustomer;
import com.due.cloud.module.customer.mapper.TbCustomerMapper;
import com.due.cloud.module.customer.service.ITbCustomerService;
import com.due.cloud.bridge.mysql.service.imple.TableDataServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author HanWenGang
 * @since 2024-01-29
 */
@Service
public class TbCustomerImpl extends TableDataServiceImpl<TbCustomerMapper, TbCustomer> implements ITbCustomerService {
    @Override
    public TbCustomer selectDataByDataId(Long dataId) {
        LogicAssert.isNull(dataId, ErrorEnum.PARAMETER_ERROR);
        return this.selectById(dataId);
    }
}
