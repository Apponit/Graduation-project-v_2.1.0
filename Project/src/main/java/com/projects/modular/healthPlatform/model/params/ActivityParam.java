package com.projects.modular.healthPlatform.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 社区活动
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
@Data
public class ActivityParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    private Long activityId;

    /**
     * 活动主题
     */
    private String title;

    /**
     * 活动内容
     */
    private String context;

    /**
     * 举办时间
     */
    private String time;

    /**
     * 图片
     */
    private String pic;

    /**
     * 发起人
     */
    private Long userId;
    private int endPoint ;
    @Override
    public String checkParam() {
        return null;
    }

}
