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
import com.projects.modular.healthPlatform.entity.RegisteredUsers;
import com.projects.modular.healthPlatform.mapper.RegisteredUsersMapper;
import com.projects.modular.healthPlatform.model.params.RegisteredUsersParam;
import com.projects.modular.healthPlatform.model.result.RegisteredUsersResult;
import com.projects.modular.healthPlatform.service.RegisteredUsersService;

import cn.stylefeng.roses.core.util.ToolUtil;

/**
 * <p>
 * 注册用户 服务实现类
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
@Service
public class RegisteredUsersServiceImpl extends ServiceImpl<RegisteredUsersMapper, RegisteredUsers> implements RegisteredUsersService {

    @Override
    public void add(RegisteredUsersParam param){
        RegisteredUsers entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(RegisteredUsersParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(RegisteredUsersParam param){
        RegisteredUsers oldEntity = getOldEntity(param);
        RegisteredUsers newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public RegisteredUsersResult findBySpec(RegisteredUsersParam param){
        return null;
    }

    @Override
    public List<RegisteredUsersResult> findListBySpec(RegisteredUsersParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(RegisteredUsersParam param){
        Page pageContext = getPageContext();
        QueryWrapper<RegisteredUsers> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(RegisteredUsersParam param){
        return param.getUserId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private RegisteredUsers getOldEntity(RegisteredUsersParam param) {
        return this.getById(getKey(param));
    }

    private RegisteredUsers getEntity(RegisteredUsersParam param) {
        RegisteredUsers entity = new RegisteredUsers();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
