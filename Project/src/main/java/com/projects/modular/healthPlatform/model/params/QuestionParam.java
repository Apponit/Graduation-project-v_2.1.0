package com.projects.modular.healthPlatform.model.params;

import java.io.Serializable;
import java.util.Date;

import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
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
public class QuestionParam implements Serializable, BaseValidatingParam {

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

    @Override
    public String checkParam() {
        return null;
    }

}
