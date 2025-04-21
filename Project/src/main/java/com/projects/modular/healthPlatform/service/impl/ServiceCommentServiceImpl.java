package com.projects.modular.healthPlatform.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.projects.core.beetl.LayuiPageFactory;
import com.projects.core.beetl.LayuiPageInfo;
import com.projects.modular.healthPlatform.entity.ServiceComment;
import com.projects.modular.healthPlatform.mapper.ServiceCommentMapper;
import com.projects.modular.healthPlatform.model.params.ServiceCommentParam;
import com.projects.modular.healthPlatform.model.result.ServiceCommentResult;
import  com.projects.modular.healthPlatform.service.ServiceCommentService;

import cn.stylefeng.roses.core.util.ToolUtil;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
@Service
public class ServiceCommentServiceImpl extends ServiceImpl<ServiceCommentMapper, ServiceComment> implements ServiceCommentService {

    @Override
    public void add(ServiceCommentParam param){
        ServiceComment entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(ServiceCommentParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(ServiceCommentParam param){
        ServiceComment oldEntity = getOldEntity(param);
        ServiceComment newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public ServiceCommentResult findBySpec(ServiceCommentParam param){
        return null;
    }

    @Override
    public List<ServiceCommentResult> findListBySpec(ServiceCommentParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(ServiceCommentParam param){
        Page pageContext = getPageContext();
        QueryWrapper<ServiceComment> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(ServiceCommentParam param){
        return param.getCommentId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private ServiceComment getOldEntity(ServiceCommentParam param) {
        return this.getById(getKey(param));
    }

    private ServiceComment getEntity(ServiceCommentParam param) {
        ServiceComment entity = new ServiceComment();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
