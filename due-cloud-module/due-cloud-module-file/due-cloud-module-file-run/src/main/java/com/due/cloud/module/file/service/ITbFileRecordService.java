package com.due.cloud.module.file.service;

import com.due.cloud.module.file.doamin.request.CreateFile;
import com.due.cloud.module.file.entity.TbFileRecord;
import com.due.cloud.bridge.mysql.service.ITableDataService;

/**
 * <p>
 * 文件记录 服务类
 * </p>
 *
 * @author HanWenGang
 * @since 2024-01-19
 */
public interface ITbFileRecordService extends ITableDataService<TbFileRecord> {

    /**
     *  文件上传
     * @param createFile
     * @return
     */
    Long uploadFile(CreateFile createFile);
}
