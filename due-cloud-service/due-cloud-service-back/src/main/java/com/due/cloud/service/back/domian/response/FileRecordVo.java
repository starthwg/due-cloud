package com.due.cloud.service.back.domian.response;

import com.due.basic.tookit.doamin.BasicDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class FileRecordVo extends BasicDomain<FileRecordVo> {

    /**
     * 文件主键
     */
    private Long dataId;


    /**
     * 文件上传时间
     */
    private Date createTime;

    /**
     * 原始文件名称
     */
    private String fileName;


    /**
     * 文件数据
     */
    private byte[] fileData;
}
