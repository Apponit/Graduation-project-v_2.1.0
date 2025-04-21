package com.projects.modular.healthPlatform.model.result;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * <p>
 * 留言管理
 * </p>
 *
 * @author demo
 * @since 2025-01-18
 */
@Data
public class QuestionResult implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long questionId;

    /**
     * 具体问题
     */
    private String context;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 提问人
     */
    private Long userId;

    /**
     * 父级id
     */
    private Long pid;
    
    private String userName;
    private boolean t;
    
    private String head;
    
    private String nickName;
}
