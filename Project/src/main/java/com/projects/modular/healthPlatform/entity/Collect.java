package com.projects.modular.healthPlatform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 收藏
 * </p>
 *
 * @author demo
 * @since 2025-01-18
 */
@TableName("t_collect")
public class Collect implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "collect_id", type = IdType.ID_WORKER)
    private Long collectId;

    /**
     * 用户
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 健康资讯
     */
    @TableField("news_id")
    private Long newsId;


    public Long getCollectId() {
        return collectId;
    }

    public void setCollectId(Long collectId) {
        this.collectId = collectId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    @Override
    public String toString() {
        return "Collect{" +
        "collectId=" + collectId +
        ", userId=" + userId +
        ", newsId=" + newsId +
        "}";
    }
}
