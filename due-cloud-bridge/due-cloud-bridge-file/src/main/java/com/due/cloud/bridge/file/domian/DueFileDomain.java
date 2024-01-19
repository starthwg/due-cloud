package com.due.cloud.bridge.file.domian;

import com.due.basic.tookit.doamin.BasicDomain;
import com.due.cloud.bridge.file.support.FileClientEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class DueFileDomain<T> extends BasicDomain<T> {


    /**
     * 存储类型
     */
    private FileClientEnum clientEnum;


    /**
     * 桶的名称
     */
    private String bucketName;
}
