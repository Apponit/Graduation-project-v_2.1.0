package com.projects.modular.healthPlatform.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.projects.core.beetl.LayuiPageInfo;
import com.projects.modular.healthPlatform.entity.Type;
import com.projects.modular.healthPlatform.model.params.TypeParam;
import com.projects.modular.healthPlatform.model.result.TypeResult;

/**
 * <p>
 * 分类 服务类
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
public interface TypeService extends IService<Type> {

    /**
     * 新增
     *
     * @author demo
     * @Date 2025-01-04
     */
    void add(TypeParam param);

    /**
     * 删除
     *
     * @author demo
     * @Date 2025-01-04
     */
    void delete(TypeParam param);

    /**
     * 更新
     *
     * @author demo
     * @Date 2025-01-04
     */
    void update(TypeParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author demo
     * @Date 2025-01-04
     */
    TypeResult findBySpec(TypeParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author demo
     * @Date 2025-01-04
     */
    List<TypeResult> findListBySpec(TypeParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author demo
     * @Date 2025-01-04
     */
     LayuiPageInfo findPageBySpec(TypeParam param);

}
