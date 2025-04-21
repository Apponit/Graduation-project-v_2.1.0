package com.projects.modular.healthPlatform.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 一键求救
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
@Data
public class LocationParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    private Long locationId;

    /**
     * 经度
     */
    private String lat;

    /**
     * 纬度
     */
    private String lng;

    /**
     * 上报时间
     */
    private Date time;

    /**
     * 用户
     */
    private Long userId;

    /**
     * 状态
     */
    private Integer status;

    @Override
    public String checkParam() {
        return null;
    }

}
