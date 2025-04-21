package com.projects.modular.healthPlatform.model.result;

import lombok.Data;
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
public class CollectResult implements Serializable {

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

}
