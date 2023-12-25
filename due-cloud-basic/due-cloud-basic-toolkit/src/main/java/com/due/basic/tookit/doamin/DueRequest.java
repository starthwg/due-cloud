package com.due.basic.tookit.doamin;

import com.due.basic.tookit.enums.ChannelEnum;
import com.due.basic.tookit.enums.ModuleCodeEnum;
import com.due.basic.tookit.enums.ModuleServiceScene;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 服务之间调的描述
 */
@Data
@Accessors(chain = true)
public class DueRequest implements Domain {

    private static final long serialVersionUID = 42L;

    /**
     * 服务模块
     */
    @NotNull(message = "服务请求模块不能为空")
    private ModuleCodeEnum moduleRequestCode;


    /**
     * 服务响应模块
     */
    @NotNull(message = "服务响应模块不能为空")
    private ModuleCodeEnum moduleResponseCode;

    /**
     * 服务调用类型
     */
    @NotNull(message = "服务调用类型")
    private ModuleServiceScene serviceScene;


    /**
     * 渠道类型
     */
    @NotNull(message = "渠道类型不能为空")
    private ChannelEnum channelEnum;

//    private String

    /**
     * 服务调用时间
     */
    @NotNull(message = "服务调用时间不能为空")
    private Date rpcTime;


    /**
     * 用户ID
     */
    private Long memberId;

    /**
     * 全局请求流水号
     */
    @NotBlank(message = "全局请求流水号不能为空")
    private String serialNo;


    public static DueRequest of() {
        return new DueRequest();
    }
}
