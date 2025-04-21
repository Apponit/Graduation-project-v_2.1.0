package com.projects.modular.healthPlatform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 活动报名人
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
@TableName("t_activity_user")
public class ActivityUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "activity_user_id", type = IdType.ID_WORKER)
    private Long activityUserId;

    /**
     * 活动
     */
    @TableField("activity_id")
    private Long activityId;

    /**
     * 报名人
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 报名时间
     */
    @TableField("time")
    private Date time;


    public Long getActivityUserId() {
        return activityUserId;
    }

    public void setActivityUserId(Long activityUserId) {
        this.activityUserId = activityUserId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
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

    @Override
    public String toString() {
        return "ActivityUser{" +
        "activityUserId=" + activityUserId +
        ", activityId=" + activityId +
        ", userId=" + userId +
        ", time=" + time +
        "}";
    }
}
