package com.projects.modular.healthPlatform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 一键求救
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
@TableName("t_location")
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "location_id", type = IdType.ID_WORKER)
    private Long locationId;

    /**
     * 经度
     */
    @TableField("lat")
    private String lat;

    /**
     * 纬度
     */
    @TableField("lng")
    private String lng;

    /**
     * 上报时间
     */
    @TableField("time")
    private Date time;

    /**
     * 用户
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 状态
     */
    @TableField("status")
    private Integer status;


    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Location{" +
        "locationId=" + locationId +
        ", lat=" + lat +
        ", lng=" + lng +
        ", time=" + time +
        ", userId=" + userId +
        ", status=" + status +
        "}";
    }
}
