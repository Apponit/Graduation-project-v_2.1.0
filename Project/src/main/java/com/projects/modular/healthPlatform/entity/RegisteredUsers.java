package com.projects.modular.healthPlatform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 注册用户
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
@TableName("t_registered_users")
public class RegisteredUsers implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "user_id", type = IdType.ID_WORKER)
    private Long userId;

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 密码
     */
    @TableField("pass_word")
    private String passWord;

    /**
     * 用户
     */
    @TableField("user_name")
    private String userName;

    /**
     * 头像
     */
    @TableField("pic")
    private String pic;

    @TableField("address")
    private String address;
    
    
    @TableField("type")
    private String type;
    
    
    
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "RegisteredUsers{" +
        "userId=" + userId +
        ", name=" + name +
        ", passWord=" + passWord +
        ", userName=" + userName +
        ", pic=" + pic +
        "}";
    }
}
