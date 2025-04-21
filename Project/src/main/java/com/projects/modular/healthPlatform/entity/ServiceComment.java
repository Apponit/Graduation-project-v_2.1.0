package com.projects.modular.healthPlatform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 评论
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
@TableName("t_service_comment")
public class ServiceComment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "comment_id", type = IdType.ID_WORKER)
    private Long commentId;

    /**
     * 内容
     */
    @TableField("content")
    private String content;

    /**
     * 评论人
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 帖子或兼职
     */
    @TableField("service_id")
    private Long serviceId;

    /**
     * 评论时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;


    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ServiceComment{" +
        "commentId=" + commentId +
        ", content=" + content +
        ", userId=" + userId +
        ", serviceId=" + serviceId +
        ", createTime=" + createTime +
        "}";
    }
}
