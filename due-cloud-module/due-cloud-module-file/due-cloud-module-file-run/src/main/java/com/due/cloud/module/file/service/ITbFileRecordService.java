package com.due.cloud.module.file.service;

import com.due.cloud.module.file.doamin.request.CreateFileRecord;
import com.due.cloud.module.file.doamin.response.FileRecordData;
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
     * @param createFileRecord
     * @return
     */
    Long uploadFile(CreateFileRecord createFileRecord);

    /**
     *  通过主键获取详情
     * @param dataId
     * @return
     */
    TbFileRecord selectDataByDataId(Long dataId);

    /**
     *  获取文件详情，包含文件数据
     * @param dataId
     * @return
     */
    FileRecordData selectDataAndDataByDataId(Long dataId);
}
