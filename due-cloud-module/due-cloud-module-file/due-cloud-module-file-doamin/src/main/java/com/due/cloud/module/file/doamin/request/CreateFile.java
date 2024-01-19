package com.due.cloud.module.file.doamin.request;

import com.due.basic.tookit.doamin.BasicDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateFile extends BasicDomain<CreateFile> {

    /**
     * 文件名称
     */
    @NotBlank(message = "文件名称不能为空")
    private String fileName;

    /**
     * 文件类型
     */
    @NotNull(message = "文件内容不能为空")
    private byte[] fileData;
}
