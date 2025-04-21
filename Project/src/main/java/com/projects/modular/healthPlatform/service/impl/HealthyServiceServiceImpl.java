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
import com.projects.modular.healthPlatform.entity.HealthyService;
import com.projects.modular.healthPlatform.mapper.HealthyServiceMapper;
import com.projects.modular.healthPlatform.model.params.HealthyServiceParam;
import com.projects.modular.healthPlatform.model.result.HealthyServiceResult;
import  com.projects.modular.healthPlatform.service.HealthyServiceService;

import cn.stylefeng.roses.core.util.ToolUtil;

/**
 * <p>
 * 健康服务 服务实现类
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
@Service
public class HealthyServiceServiceImpl extends ServiceImpl<HealthyServiceMapper, HealthyService> implements HealthyServiceService {

    @Override
    public void add(HealthyServiceParam param){
        HealthyService entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(HealthyServiceParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(HealthyServiceParam param){
        HealthyService oldEntity = getOldEntity(param);
        HealthyService newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public HealthyServiceResult findBySpec(HealthyServiceParam param){
        return null;
    }

    @Override
    public List<HealthyServiceResult> findListBySpec(HealthyServiceParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(HealthyServiceParam param){
        Page pageContext = getPageContext();
        QueryWrapper<HealthyService> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(HealthyServiceParam param){
        return param.getServiceId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private HealthyService getOldEntity(HealthyServiceParam param) {
        return this.getById(getKey(param));
    }

    private HealthyService getEntity(HealthyServiceParam param) {
        HealthyService entity = new HealthyService();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
