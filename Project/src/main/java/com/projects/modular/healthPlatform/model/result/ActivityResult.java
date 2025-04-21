package com.projects.modular.healthPlatform.model.result;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.projects.modular.healthPlatform.entity.RegisteredUsers;

import lombok.Data;

/**
 * <p>
 * 社区活动
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
@Data
public class ActivityResult implements Serializable {

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
    private Date time;

    /**
     * 图片
     */
    private String pic;

    /**
     * 发起人
     */
    private Long userId;
    
    private String name;
    
    
    private int status ;
    private int endPoint ;
    
    private int coount ;
    
    private List<RegisteredUsers> user ;
    

}
