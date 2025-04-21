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
import com.projects.modular.healthPlatform.entity.Activity;
import com.projects.modular.healthPlatform.mapper.ActivityMapper;
import com.projects.modular.healthPlatform.model.params.ActivityParam;
import com.projects.modular.healthPlatform.model.result.ActivityResult;
import com.projects.modular.healthPlatform.service.ActivityService;
import com.projects.modular.system.entity.User;
import com.projects.modular.system.mapper.UserMapper;

import cn.stylefeng.roses.core.util.ToolUtil;

/**
 * <p>
 * 社区活动 服务实现类
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {
	@Autowired
	private UserMapper userMapper;
    @Override
    public void add(ActivityParam param){
        Activity entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(ActivityParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(ActivityParam param){
        Activity oldEntity = getOldEntity(param);
        Activity newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public ActivityResult findBySpec(ActivityParam param){
        return null;
    }

    @Override
    public List<ActivityResult> findListBySpec(ActivityParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(ActivityParam param){
        Page pageContext = getPageContext();
        QueryWrapper<Activity> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.orderByDesc("time");
        IPage page = this.page(pageContext, objectQueryWrapper);
        List<Activity> list = page.getRecords();
        List<ActivityResult> listResult = new ArrayList<>();
        for (Activity activity : list) {
        	ActivityResult result = new ActivityResult();
        	ToolUtil.copyProperties(activity, result);
        	User user = userMapper.selectById(activity.getUserId());
        	if(null !=user) {
        		result.setName(user.getName());
        	}
        	listResult.add(result);
		}
        page.setRecords(listResult);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(ActivityParam param){
        return param.getActivityId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private Activity getOldEntity(ActivityParam param) {
        return this.getById(getKey(param));
    }

    private Activity getEntity(ActivityParam param) {
        Activity entity = new Activity();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
