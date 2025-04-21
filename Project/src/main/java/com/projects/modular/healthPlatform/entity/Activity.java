package com.projects.modular.healthPlatform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 社区活动
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
@TableName("t_activity")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "activity_id", type = IdType.ID_WORKER)
    private Long activityId;

    /**
     * 活动主题
     */
    @TableField("title")
    private String title;

    /**
     * 活动内容
     */
    @TableField("context")
    private String context;

    /**
     * 举办时间
     */
    @TableField("time")
    private Date time;

    /**
     * 图片
     */
    @TableField("pic")
    private String pic;

    /**
     * 发起人
     */
    @TableField("user_id")
    private Long userId;
    @TableField("end_point")
    private int endPoint ;
    
    
    
    
    public int getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(int endPoint) {
		this.endPoint = endPoint;
	}

	public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Activity{" +
        "activityId=" + activityId +
        ", title=" + title +
        ", context=" + context +
        ", time=" + time +
        ", pic=" + pic +
        ", userId=" + userId +
        "}";
    }
}
