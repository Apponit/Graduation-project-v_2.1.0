package com.projects.modular.healthPlatform.model.result;

import lombok.Data;
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
public class TypeResult implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long typeId;

    /**
     * 分类名称
     */
    private String name;

}
