package com.projects.modular.system.entity;

import lombok.Data;

@Data
public class Login {
	private String token;
	  /**
     * 头像
     */
    private String headIcon;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 手机
     */
    private String phone;

    /**
     * 性别
     */
    private String sex;

    private String	card ;
    
    private String code ;
    
    
    private String sdCard ;
    
    
    private String address ;
    
   
    private String bank ;
    
   
    private String openBank ;
    
   private Long id ;
}
