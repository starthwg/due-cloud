package com.due.cloud.bridge.file.domian;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

@Builder
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DueGetObjectResult extends DueFileDomain<DueGetObjectResult> {

    /**
     * 文件名称
     */
    private String fileName;


    /**
     * 文件出入流
     */
    private InputStream inputStream;


    /**
     * 文件字节
     */
    private byte[] fileData;
}
