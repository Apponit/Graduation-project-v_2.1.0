package com.projects.modular.healthPlatform.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 健康档案
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
@Data
public class RecordParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    private Long recordId;

    /**
     * 用户
     */
    private Long userId;

    /**
     * 检查时间
     */
    private Date time;

    /**
     * 详细记录
     */
    private String record;

    /**
     * 报告
     */
    private String report;

    /**
     * 操作人
     */
    private Long opeId;

    /**
     * 建议
     */
    private String advise;

    @Override
    public String checkParam() {
        return null;
    }

}
