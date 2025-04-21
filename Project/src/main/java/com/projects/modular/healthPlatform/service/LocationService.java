package com.projects.modular.healthPlatform.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.projects.core.beetl.LayuiPageInfo;
import com.projects.modular.healthPlatform.entity.Location;
import com.projects.modular.healthPlatform.model.params.LocationParam;
import com.projects.modular.healthPlatform.model.result.LocationResult;

/**
 * <p>
 * 一键求救 服务类
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
public interface LocationService extends IService<Location> {

    /**
     * 新增
     *
     * @author demo
     * @Date 2025-01-04
     */
    void add(LocationParam param);

    /**
     * 删除
     *
     * @author demo
     * @Date 2025-01-04
     */
    void delete(LocationParam param);

    /**
     * 更新
     *
     * @author demo
     * @Date 2025-01-04
     */
    void update(LocationParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author demo
     * @Date 2025-01-04
     */
    LocationResult findBySpec(LocationParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author demo
     * @Date 2025-01-04
     */
    List<LocationResult> findListBySpec(LocationParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author demo
     * @Date 2025-01-04
     */
     LayuiPageInfo findPageBySpec(LocationParam param);

}
