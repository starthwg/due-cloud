package com.due.basic.tookit.exception;

import com.due.basic.tookit.enums.ErrorEnum;
import com.due.basic.tookit.utils.LogicUtil;
import lombok.Getter;

/**
 *  自定义异常信息
 */
@Getter
public class LogicException extends RuntimeException {

    private final int code;

    private String desc;

    public LogicException(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public LogicException(ErrorEnum errorEnum) {
        super(errorEnum.getMessage());
        this.code = errorEnum.getCode();
        this.desc = errorEnum.getMessage();
    }

    public LogicException(ErrorEnum errorEnum, String message) {
        super(message);
        String msg = errorEnum.getMessage();
        if (LogicUtil.isAllNotBlank(message)) {
            msg = msg + " :" + message;
        }
        this.code = errorEnum.getCode();
        this.desc = msg;
//        if (LogicUtil.isAllNotBlank(message)) {
//            this.desc = this.desc + ":" + message;
//        }
    }

    public LogicException(ErrorEnum error, String desc, Exception e) {
        super(desc);
        this.code = error.getCode();
        this.desc = error.getMessage();
        if (e != null) {
            this.desc = this.desc + "：" + desc;
        }
        if (e != null) {
            this.desc = this.desc + "，原因：" + e.getLocalizedMessage();
        }
    }

    public LogicException(Exception e) {
        super(e.getMessage());
        this.code = ErrorEnum.other_ERROR.getCode();
        this.desc = ErrorEnum.other_ERROR.getMessage();
    }
}
