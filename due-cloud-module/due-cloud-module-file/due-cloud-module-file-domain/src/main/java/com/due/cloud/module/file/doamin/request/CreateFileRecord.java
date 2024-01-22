package com.due.cloud.module.file.doamin.request;

import com.due.basic.tookit.doamin.BasicDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateFileRecord extends BasicDomain<CreateFileRecord> {

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


    /**
     *  文件大小
     */
    @NotNull(message = "文件大小不能为空")
    private BigDecimal fileSize;

}
