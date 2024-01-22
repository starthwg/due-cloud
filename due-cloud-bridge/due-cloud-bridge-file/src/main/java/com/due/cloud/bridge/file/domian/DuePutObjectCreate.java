package com.due.cloud.bridge.file.domian;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.InputStream;

/**
 * 上传文件的对象
 *
 * @author hanwengang
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
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


    /**
     *  文件内容
     */
    private byte[] fileData;

    public DuePutObjectCreate() {
    }
}
