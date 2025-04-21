package com.projects.modular.healthPlatform.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.projects.core.beetl.LayuiPageInfo;
import com.projects.modular.healthPlatform.entity.Comment;
import com.projects.modular.healthPlatform.model.params.CommentParam;
import com.projects.modular.healthPlatform.model.result.CommentResult;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
public interface CommentService extends IService<Comment> {

    /**
     * 新增
     *
     * @author demo
     * @Date 2025-01-04
     */
    void add(CommentParam param);

    /**
     * 删除
     *
     * @author demo
     * @Date 2025-01-04
     */
    void delete(CommentParam param);

    /**
     * 更新
     *
     * @author demo
     * @Date 2025-01-04
     */
    void update(CommentParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author demo
     * @Date 2025-01-04
     */
    CommentResult findBySpec(CommentParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author demo
     * @Date 2025-01-04
     */
    List<CommentResult> findListBySpec(CommentParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author demo
     * @Date 2025-01-04
     */
     LayuiPageInfo findPageBySpec(CommentParam param);

}
