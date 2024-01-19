package com.due.cloud.bridge.file.domian;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DueBucketCreate extends DueFileDomain<DueBucketCreate> {

    /**
     *  桶的类型
     */
    private String bucketName;


}
