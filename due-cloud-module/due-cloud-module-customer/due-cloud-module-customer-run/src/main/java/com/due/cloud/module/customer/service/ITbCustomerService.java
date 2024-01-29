package com.due.cloud.module.customer.service;

import com.due.cloud.module.customer.entity.TbCustomer;
import com.due.cloud.bridge.mysql.service.ITableDataService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author HanWenGang
 * @since 2024-01-29
 */

public interface ITbCustomerService extends ITableDataService<TbCustomer> {

    /**
     * 通过数据主键获取记录
     *
     * @param dataId Long类型
     * @return TbCustomer 类型
     */
    TbCustomer selectDataByDataId(Long dataId);
}
