package com.projects.modular.healthPlatform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 健康档案
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
@TableName("t_record")
public class Record implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "record_id", type = IdType.ID_WORKER)
    private Long recordId;

    /**
     * 用户
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 检查时间
     */
    @TableField("time")
    private Date time;

    /**
     * 详细记录
     */
    @TableField("record")
    private String record;

    /**
     * 报告
     */
    @TableField("report")
    private String report;

    /**
     * 操作人
     */
    @TableField("ope_id")
    private Long opeId;

    /**
     * 建议
     */
    @TableField("advise")
    private String advise;


    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public Long getOpeId() {
        return opeId;
    }

    public void setOpeId(Long opeId) {
        this.opeId = opeId;
    }

    public String getAdvise() {
        return advise;
    }

    public void setAdvise(String advise) {
        this.advise = advise;
    }

    @Override
    public String toString() {
        return "Record{" +
        "recordId=" + recordId +
        ", userId=" + userId +
        ", time=" + time +
        ", record=" + record +
        ", report=" + report +
        ", opeId=" + opeId +
        ", advise=" + advise +
        "}";
    }
}
