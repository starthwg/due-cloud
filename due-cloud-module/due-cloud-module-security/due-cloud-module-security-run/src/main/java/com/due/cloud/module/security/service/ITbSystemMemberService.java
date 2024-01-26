package com.due.cloud.module.security.service;

import com.due.cloud.module.security.entity.TbSystemMember;
import com.due.cloud.bridge.mysql.service.ITableDataService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author HanWenGang
 * @since 2023-12-29
 */
@CacheConfig(cacheNames = "member:")
public interface ITbSystemMemberService extends ITableDataService<TbSystemMember> {


    @Cacheable(key = "'username:' + #userName", unless = "#result == null ")
    TbSystemMember selectSystemMemberByUsername(String userName);
}
