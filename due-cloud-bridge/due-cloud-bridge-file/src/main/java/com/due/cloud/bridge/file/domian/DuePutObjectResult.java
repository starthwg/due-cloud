package com.due.cloud.bridge.file.domian;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 上传文件返回对象
 *
 * @author hanwengang
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DuePutObjectResult extends DueFileDomain<DuePutObjectResult> {

    /**
     * 文件路径
     */
    private String filePath;


    /**
     * 文件类型
     */
    private String fileType;


    private String newFileName;
}
