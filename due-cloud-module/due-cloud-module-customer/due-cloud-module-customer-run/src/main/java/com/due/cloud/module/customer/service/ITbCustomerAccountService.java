package com.due.cloud.module.customer.service;

import com.due.cloud.module.customer.api.domain.response.CustomerAccount;
import com.due.cloud.module.customer.entity.TbCustomerAccount;
import com.due.cloud.bridge.mysql.service.ITableDataService;

/**
 * <p>
 * 用户账户表 服务类
 * </p>
 *
 * @author HanWenGang
 * @since 2024-01-29
 */
public interface ITbCustomerAccountService extends ITableDataService<TbCustomerAccount> {


    TbCustomerAccount selectData(TbCustomerAccount account);
}
