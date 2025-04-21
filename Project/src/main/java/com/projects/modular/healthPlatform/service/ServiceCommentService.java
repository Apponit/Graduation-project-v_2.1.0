package com.projects.modular.healthPlatform.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.projects.core.beetl.LayuiPageInfo;
import com.projects.modular.healthPlatform.entity.ServiceComment;
import com.projects.modular.healthPlatform.model.params.ServiceCommentParam;
import com.projects.modular.healthPlatform.model.result.ServiceCommentResult;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
public interface ServiceCommentService extends IService<ServiceComment> {

    /**
     * 新增
     *
     * @author demo
     * @Date 2025-01-04
     */
    void add(ServiceCommentParam param);

    /**
     * 删除
     *
     * @author demo
     * @Date 2025-01-04
     */
    void delete(ServiceCommentParam param);

    /**
     * 更新
     *
     * @author demo
     * @Date 2025-01-04
     */
    void update(ServiceCommentParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author demo
     * @Date 2025-01-04
     */
    ServiceCommentResult findBySpec(ServiceCommentParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author demo
     * @Date 2025-01-04
     */
    List<ServiceCommentResult> findListBySpec(ServiceCommentParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author demo
     * @Date 2025-01-04
     */
     LayuiPageInfo findPageBySpec(ServiceCommentParam param);

}
