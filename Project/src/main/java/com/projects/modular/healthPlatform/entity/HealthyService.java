package com.projects.modular.healthPlatform.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 健康服务
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
@TableName("t_healthy_service")
public class HealthyService implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "service_id", type = IdType.ID_WORKER)
    private Long serviceId;

    /**
     * 服务名称
     */
    @TableField("name")
    private String name;

    /**
     * 服务介绍
     */
    @TableField("intro")
    private String intro;

    /**
     * 服务封面
     */
    @TableField("cover")
    private String cover;

    /**
     * 服务费
     */
    @TableField("price")
    private BigDecimal price;

    /**
     * 联系电话
     */
    @TableField("tel")
    private String tel;


    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "HealthyService{" +
        "serviceId=" + serviceId +
        ", name=" + name +
        ", intro=" + intro +
        ", cover=" + cover +
        ", price=" + price +
        ", tel=" + tel +
        "}";
    }
}
