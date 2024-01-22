package com.due.cloud.module.file.doamin.response;

import com.due.basic.tookit.doamin.BasicDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class FileRecord extends BasicDomain<FileRecord> {

    /**
     * 数据主键
     */
    private Long dataId;

    /**
     * 创建时间
     */
    private Date createTime;


    /**
     * 文件大小
     */
    private BigDecimal fileSize;

    /**
     * 文件后置
     */
    private String fileType;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 服务器类型：1-minIo，2-OOS
     */
    private String fileDirect;

    /**
     * 原始文件名称
     */
    private String fileRawName;

    /**
     * 新文件名称
     */
    private String fileNewName;
}
