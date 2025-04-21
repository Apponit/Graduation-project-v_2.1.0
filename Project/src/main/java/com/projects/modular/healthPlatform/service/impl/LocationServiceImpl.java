package com.projects.modular.healthPlatform.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.projects.core.beetl.LayuiPageFactory;
import com.projects.core.beetl.LayuiPageInfo;
import com.projects.modular.healthPlatform.entity.Location;
import com.projects.modular.healthPlatform.entity.RegisteredUsers;
import com.projects.modular.healthPlatform.mapper.LocationMapper;
import com.projects.modular.healthPlatform.mapper.RegisteredUsersMapper;
import com.projects.modular.healthPlatform.model.params.LocationParam;
import com.projects.modular.healthPlatform.model.result.LocationResult;
import  com.projects.modular.healthPlatform.service.LocationService;

import cn.stylefeng.roses.core.util.ToolUtil;

/**
 * <p>
 * 一键求救 服务实现类
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
@Service
public class LocationServiceImpl extends ServiceImpl<LocationMapper, Location> implements LocationService {
	@Autowired
	private RegisteredUsersMapper  registeredUsersMapper;
    @Override
    public void add(LocationParam param){
        Location entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(LocationParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(LocationParam param){
        Location oldEntity = getOldEntity(param);
        Location newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public LocationResult findBySpec(LocationParam param){
        return null;
    }

    @Override
    public List<LocationResult> findListBySpec(LocationParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(LocationParam param){
        Page pageContext = getPageContext();
        QueryWrapper<Location> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        List<Location> records = page.getRecords();
        List<LocationResult> list = new ArrayList<>();
        for (Location location : records) {
        	LocationResult result = new LocationResult();
        	ToolUtil.copyProperties(location, result);
        	RegisteredUsers registeredUsers = registeredUsersMapper.selectById(location.getUserId());
        	if(null !=registeredUsers) {
        		result.setName(registeredUsers.getName());
        		result.setTel(registeredUsers.getAddress());
        	}
        	list.add(result);
		}
        page.setRecords(list);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(LocationParam param){
        return param.getLocationId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private Location getOldEntity(LocationParam param) {
        return this.getById(getKey(param));
    }

    private Location getEntity(LocationParam param) {
        Location entity = new Location();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
