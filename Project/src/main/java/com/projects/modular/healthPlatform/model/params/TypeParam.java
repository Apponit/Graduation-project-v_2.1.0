package com.projects.modular.healthPlatform.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 分类
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
@Data
public class TypeParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    private Long typeId;

    /**
     * 分类名称
     */
    private String name;

    @Override
    public String checkParam() {
        return null;
    }

}
