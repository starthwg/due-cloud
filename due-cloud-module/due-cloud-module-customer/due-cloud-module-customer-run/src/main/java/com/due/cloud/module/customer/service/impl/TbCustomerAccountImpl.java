package com.due.cloud.module.customer.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.due.basic.tookit.utils.LogicUtil;
import com.due.cloud.module.customer.entity.TbCustomerAccount;
import com.due.cloud.module.customer.mapper.TbCustomerAccountMapper;
import com.due.cloud.module.customer.service.ITbCustomerAccountService;
import com.due.cloud.bridge.mysql.service.imple.TableDataServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户账户表 服务实现类
 * </p>
 *
 * @author HanWenGang
 * @since 2024-01-29
 */
@Service
public class TbCustomerAccountImpl extends TableDataServiceImpl<TbCustomerAccountMapper, TbCustomerAccount> implements ITbCustomerAccountService {
    @Override
    public TbCustomerAccount selectData(TbCustomerAccount account) {
        LambdaQueryChainWrapper<TbCustomerAccount> wrapper = this.getWrapper(account);
        List<TbCustomerAccount> list = wrapper.list();
        if (LogicUtil.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }


    private LambdaQueryChainWrapper<TbCustomerAccount> getWrapper(TbCustomerAccount condition) {
        LambdaQueryChainWrapper<TbCustomerAccount> wrapper = this.lambdaQuery();
        wrapper.orderByDesc(TbCustomerAccount::getCreateTime);
        if (null == condition) {
            return wrapper;
        }
        wrapper.eq(null != condition.getType(), TbCustomerAccount::getType, condition.getType());
        wrapper.eq(LogicUtil.isAllNotBlank(condition.getSecret()), TbCustomerAccount::getSecret, condition.getSecret());
        return wrapper;
    }
}
