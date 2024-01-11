package com.due.cloud.bridge.mysql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.due.cloud.bridge.mysql.domian.BasicData;

/**
 *  mybatis的基础mapper类
 * @param <T>
 */
public interface IBasicDataMapper<T extends BasicData> extends BaseMapper<T> {
}
