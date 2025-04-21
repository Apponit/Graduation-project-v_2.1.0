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
import com.projects.modular.healthPlatform.entity.Type;
import com.projects.modular.healthPlatform.mapper.TypeMapper;
import com.projects.modular.healthPlatform.model.params.TypeParam;
import com.projects.modular.healthPlatform.model.result.TypeResult;
import com.projects.modular.healthPlatform.service.TypeService;

import cn.stylefeng.roses.core.util.ToolUtil;

/**
 * <p>
 * 分类 服务实现类
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {

    @Override
    public void add(TypeParam param){
        Type entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(TypeParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(TypeParam param){
        Type oldEntity = getOldEntity(param);
        Type newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public TypeResult findBySpec(TypeParam param){
        return null;
    }

    @Override
    public List<TypeResult> findListBySpec(TypeParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(TypeParam param){
        Page pageContext = getPageContext();
        QueryWrapper<Type> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(TypeParam param){
        return param.getTypeId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private Type getOldEntity(TypeParam param) {
        return this.getById(getKey(param));
    }

    private Type getEntity(TypeParam param) {
        Type entity = new Type();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
