package com.due.cloud.bridge.file.domian;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.InputStream;

/**
 * 上传文件的对象
 *
 * @author hanwengang
 */
@Builder
@Getter
@EqualsAndHashCode(callSuper = true)
public class DuePutObjectCreate extends DueFileDomain<DuePutObjectCreate> {

    /**
     * 文件名称
     */
    private String fileName;


    /**
     * 新的文件名称
     */
    private String newFileName;


    /**
     * 文件流
     */
    private InputStream inputStream;


    /**
     *  文件后缀
     */
    private String filePostfix;

    public DuePutObjectCreate() {
    }
}
