package com.projects.modular.healthPlatform.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.projects.core.beetl.LayuiPageInfo;
import com.projects.modular.healthPlatform.entity.Collect;
import com.projects.modular.healthPlatform.model.params.CollectParam;
import com.projects.modular.healthPlatform.model.result.CollectResult;

/**
 * <p>
 * 收藏 服务类
 * </p>
 *
 * @author demo
 * @since 2025-01-18
 */
public interface CollectService extends IService<Collect> {

    /**
     * 新增
     *
     * @author demo
     * @Date 2025-01-18
     */
    void add(CollectParam param);

    /**
     * 删除
     *
     * @author demo
     * @Date 2025-01-18
     */
    void delete(CollectParam param);

    /**
     * 更新
     *
     * @author demo
     * @Date 2025-01-18
     */
    void update(CollectParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author demo
     * @Date 2025-01-18
     */
    CollectResult findBySpec(CollectParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author demo
     * @Date 2025-01-18
     */
    List<CollectResult> findListBySpec(CollectParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author demo
     * @Date 2025-01-18
     */
     LayuiPageInfo findPageBySpec(CollectParam param);

}
