package com.projects.modular.healthPlatform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 分类
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
@TableName("t_type")
public class Type implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "type_id", type = IdType.ID_WORKER)
    private Long typeId;

    /**
     * 分类名称
     */
    @TableField("name")
    private String name;


    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Type{" +
        "typeId=" + typeId +
        ", name=" + name +
        "}";
    }
}
