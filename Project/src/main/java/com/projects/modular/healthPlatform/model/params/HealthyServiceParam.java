package com.projects.modular.healthPlatform.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 健康服务
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
@Data
public class HealthyServiceParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    private Long serviceId;

    /**
     * 服务名称
     */
    private String name;

    /**
     * 服务介绍
     */
    private String intro;

    /**
     * 服务封面
     */
    private String cover;

    /**
     * 服务费
     */
    private BigDecimal price;

    /**
     * 联系电话
     */
    private String tel;

    @Override
    public String checkParam() {
        return null;
    }

}
