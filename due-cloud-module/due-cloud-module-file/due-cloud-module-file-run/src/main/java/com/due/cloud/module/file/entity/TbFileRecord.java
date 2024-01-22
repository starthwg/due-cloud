package com.due.cloud.module.file.entity;

import java.math.BigDecimal;

import com.due.cloud.bridge.mysql.domian.TableData;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文件记录
 * </p>
 *
 * @author HanWenGang
 * @since 2024-01-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TbFileRecord extends TableData<TbFileRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 文件大小
     */
    private BigDecimal fileSize;

    /**
     * 文件后置
     */
    private String fileType;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 服务器类型：1-minIo，2-OOS
     */
    private String fileDirect;

    /**
     * 原始文件名称
     */
    private String fileRawName;

    /**
     * 新文件名称
     */
    private String fileNewName;


}
