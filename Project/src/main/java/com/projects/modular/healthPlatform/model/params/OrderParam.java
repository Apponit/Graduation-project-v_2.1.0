package com.projects.modular.healthPlatform.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 服务预约
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
@Data
public class OrderParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    private Long orderId;

    /**
     * 服务
     */
    private Long serviceId;

    /**
     * 预约人
     */
    private Long userId;

    /**
     * 预约时间
     */
    private Date time;

    /**
     * 预约号
     */
    private String orderNo;

    /**
     * 留言
     */
    private String message;

    /**
     * 状态
     */
    private Integer status;

    @Override
    public String checkParam() {
        return null;
    }

}
