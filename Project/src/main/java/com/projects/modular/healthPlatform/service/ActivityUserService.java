package com.projects.modular.healthPlatform.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.projects.core.beetl.LayuiPageInfo;
import com.projects.modular.healthPlatform.entity.ActivityUser;
import com.projects.modular.healthPlatform.model.params.ActivityUserParam;
import com.projects.modular.healthPlatform.model.result.ActivityUserResult;

/**
 * <p>
 * 活动报名人 服务类
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
public interface ActivityUserService extends IService<ActivityUser> {

    /**
     * 新增
     *
     * @author demo
     * @Date 2025-01-04
     */
    void add(ActivityUserParam param);

    /**
     * 删除
     *
     * @author demo
     * @Date 2025-01-04
     */
    void delete(ActivityUserParam param);

    /**
     * 更新
     *
     * @author demo
     * @Date 2025-01-04
     */
    void update(ActivityUserParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author demo
     * @Date 2025-01-04
     */
    ActivityUserResult findBySpec(ActivityUserParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author demo
     * @Date 2025-01-04
     */
    List<ActivityUserResult> findListBySpec(ActivityUserParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author demo
     * @Date 2025-01-04
     */
     LayuiPageInfo findPageBySpec(ActivityUserParam param);

}
