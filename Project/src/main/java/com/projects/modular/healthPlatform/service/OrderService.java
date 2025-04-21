package com.projects.modular.healthPlatform.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.projects.core.beetl.LayuiPageInfo;
import com.projects.modular.healthPlatform.entity.Order;
import com.projects.modular.healthPlatform.model.params.OrderParam;
import com.projects.modular.healthPlatform.model.result.OrderResult;

/**
 * <p>
 * 服务预约 服务类
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
public interface OrderService extends IService<Order> {

    /**
     * 新增
     *
     * @author demo
     * @Date 2025-01-04
     */
    void add(OrderParam param);

    /**
     * 删除
     *
     * @author demo
     * @Date 2025-01-04
     */
    void delete(OrderParam param);

    /**
     * 更新
     *
     * @author demo
     * @Date 2025-01-04
     */
    void update(OrderParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author demo
     * @Date 2025-01-04
     */
    OrderResult findBySpec(OrderParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author demo
     * @Date 2025-01-04
     */
    List<OrderResult> findListBySpec(OrderParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author demo
     * @Date 2025-01-04
     */
     LayuiPageInfo findPageBySpec(OrderParam param);

}
