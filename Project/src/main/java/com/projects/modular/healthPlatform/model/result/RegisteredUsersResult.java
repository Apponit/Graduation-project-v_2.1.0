package com.projects.modular.healthPlatform.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 注册用户
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
@Data
public class RegisteredUsersResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private Long userId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 密码
     */
    private String passWord;

    /**
     * 用户
     */
    private String userName;

    /**
     * 头像
     */
    private String pic;

    private String address;
    
    private String type;
}
