package com.due.cloud.bridge.file.domian;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = true)
public class DueGetObjectCreate extends DueFileDomain<DueGetObjectCreate> {

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 新的文件名称
     */
    private String newFileName;

    /**
     * 文件名称
     */
    private String fileName;
}
