package com.due.cloud.bridge.mysql.domian;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 实体基础类
 * @author hanwengang
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class TableData<T> extends BasicData {

    /**
     * 数据主键
     */
    @TableId
    private Long dataId;


    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 数据版本
     */
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Long dataVersion;


    @TableField(fill = FieldFill.INSERT)
    @TableLogic(value = "0", delval = "1")
    private Integer dataStatus;
}
