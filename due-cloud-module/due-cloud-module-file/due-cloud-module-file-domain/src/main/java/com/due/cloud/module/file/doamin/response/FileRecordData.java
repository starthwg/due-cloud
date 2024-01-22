package com.due.cloud.module.file.doamin.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FileRecordData extends FileRecord {

    /**
     * 文件字节
     */
    private byte[] fileData;
}
