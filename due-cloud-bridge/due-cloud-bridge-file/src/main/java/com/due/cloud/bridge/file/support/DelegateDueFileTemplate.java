package com.due.cloud.bridge.file.support;

import com.due.basic.tookit.enums.ErrorEnum;
import com.due.basic.tookit.exception.LogicAssert;
import com.due.basic.tookit.exception.LogicException;
import com.due.cloud.bridge.file.config.BridgeFileProperties;
import com.due.cloud.bridge.file.domian.DueGetObjectCreate;
import com.due.cloud.bridge.file.domian.DueGetObjectResult;
import com.due.cloud.bridge.file.domian.DuePutObjectCreate;
import com.due.cloud.bridge.file.domian.DuePutObjectResult;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * 文件上传的委派类
 *
 * @author hanwengang
 */

@Data
public class DelegateDueFileTemplate implements FileTemplate {



    /**
     * 文件上传的
     */

    @Autowired
    private List<AbstractFileTemplate<?>> abstractFileTemplateList;

    /**
     * 配置信息
     */
    @Autowired
    private BridgeFileProperties fileProperties;

    public DelegateDueFileTemplate(BridgeFileProperties fileProperties) {
        this.fileProperties = fileProperties;
    }


    public DelegateDueFileTemplate() {
    }

    @Override
    public DuePutObjectResult putObject(DuePutObjectCreate putObjectCreate) throws IOException {
        return this.getFileTemplate(putObjectCreate.getClientEnum()).putObject(putObjectCreate);
    }

    @Override
    public DueGetObjectResult getObject(DueGetObjectCreate getObjectCreate) {
        return this.getFileTemplate(getObjectCreate.getClientEnum()).getObject(getObjectCreate);
    }

    private AbstractFileTemplate<?> getFileTemplate(FileClientEnum clientEnum) {
        if (null == clientEnum) {
            clientEnum = this.fileProperties.getFileClient();
        }
        LogicAssert.isNull(clientEnum, ErrorEnum.DATA_HANDLE_ERROR, "没有指定默认的文件存储！");
        FileClientEnum finalClientEnum = clientEnum;
        return abstractFileTemplateList.stream().filter(e -> e.support(finalClientEnum)).findFirst().orElseThrow(() -> new LogicException(ErrorEnum.DATA_HANDLE_ERROR, "没有找到相关的文件存在模板类型！"));
    }
}
