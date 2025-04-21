package com.projects.modular.healthPlatform.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.projects.core.beetl.LayuiPageInfo;
import com.projects.modular.healthPlatform.entity.Question;
import com.projects.modular.healthPlatform.model.params.QuestionParam;
import com.projects.modular.healthPlatform.model.result.QuestionResult;

/**
 * <p>
 * 留言管理 服务类
 * </p>
 *
 * @author demo
 * @since 2025-01-18
 */
public interface QuestionService extends IService<Question> {

    /**
     * 新增
     *
     * @author demo
     * @Date 2025-01-18
     */
    void add(QuestionParam param);

    /**
     * 删除
     *
     * @author demo
     * @Date 2025-01-18
     */
    void delete(QuestionParam param);

    /**
     * 更新
     *
     * @author demo
     * @Date 2025-01-18
     */
    void update(QuestionParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author demo
     * @Date 2025-01-18
     */
    QuestionResult findBySpec(QuestionParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author demo
     * @Date 2025-01-18
     */
    List<QuestionResult> findListBySpec(QuestionParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author demo
     * @Date 2025-01-18
     */
     LayuiPageInfo findPageBySpec(QuestionParam param);

}
