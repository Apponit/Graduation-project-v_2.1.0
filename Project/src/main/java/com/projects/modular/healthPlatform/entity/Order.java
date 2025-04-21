package com.projects.modular.healthPlatform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 服务预约
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
@TableName("t_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "order_id", type = IdType.ID_WORKER)
    private Long orderId;

    /**
     * 服务
     */
    @TableField("service_id")
    private Long serviceId;

    /**
     * 预约人
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 预约时间
     */
    @TableField("time")
    private Date time;

    /**
     * 预约号
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * 留言
     */
    @TableField("message")
    private String message;

    /**
     * 状态
     */
    @TableField("status")
    private Integer status;


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
        "orderId=" + orderId +
        ", serviceId=" + serviceId +
        ", userId=" + userId +
        ", time=" + time +
        ", orderNo=" + orderNo +
        ", message=" + message +
        ", status=" + status +
        "}";
    }
}
