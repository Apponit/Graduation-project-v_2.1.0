package com.projects.modular.healthPlatform.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 收藏
 * </p>
 *
 * @author demo
 * @since 2025-01-18
 */
@Data
public class CollectParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    private Long collectId;

    /**
     * 用户
     */
    private Long userId;

    /**
     * 健康资讯
     */
    private Long newsId;

    @Override
    public String checkParam() {
        return null;
    }

}
