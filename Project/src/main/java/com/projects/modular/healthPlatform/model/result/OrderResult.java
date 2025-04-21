package com.projects.modular.healthPlatform.model.result;

import lombok.Data;
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
public class OrderResult implements Serializable {

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
    
    private String name;
    
    private String pic;
    
    private String tel;
    
    private String userName;

}
