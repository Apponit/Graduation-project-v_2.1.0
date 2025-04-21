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
import com.projects.modular.healthPlatform.entity.Collect;
import com.projects.modular.healthPlatform.mapper.CollectMapper;
import com.projects.modular.healthPlatform.model.params.CollectParam;
import com.projects.modular.healthPlatform.model.result.CollectResult;
import com.projects.modular.healthPlatform.service.CollectService;

import cn.stylefeng.roses.core.util.ToolUtil;

/**
 * <p>
 * 收藏 服务实现类
 * </p>
 *
 * @author demo
 * @since 2025-01-18
 */
@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService {

    @Override
    public void add(CollectParam param){
        Collect entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(CollectParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(CollectParam param){
        Collect oldEntity = getOldEntity(param);
        Collect newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public CollectResult findBySpec(CollectParam param){
        return null;
    }

    @Override
    public List<CollectResult> findListBySpec(CollectParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(CollectParam param){
        Page pageContext = getPageContext();
        QueryWrapper<Collect> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(CollectParam param){
        return param.getCollectId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private Collect getOldEntity(CollectParam param) {
        return this.getById(getKey(param));
    }

    private Collect getEntity(CollectParam param) {
        Collect entity = new Collect();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
