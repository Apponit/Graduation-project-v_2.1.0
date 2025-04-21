package com.projects.modular.healthPlatform.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 新闻
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
@Data
public class NewsResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private Long newsId;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 发布时间
     */
    private Date createTime;

    /**
     * 简介
     */
    private String introduce;
    private String pic;
    private Integer status;
    private String  type;
    
    private boolean t;
}
