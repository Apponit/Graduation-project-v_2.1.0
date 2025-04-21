package com.projects.modular.healthPlatform.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 活动报名人
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
@Data
public class ActivityUserParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    private Long activityUserId;

    /**
     * 活动
     */
    private Long activityId;

    /**
     * 报名人
     */
    private Long userId;

    /**
     * 报名时间
     */
    private Date time;

    @Override
    public String checkParam() {
        return null;
    }

}
