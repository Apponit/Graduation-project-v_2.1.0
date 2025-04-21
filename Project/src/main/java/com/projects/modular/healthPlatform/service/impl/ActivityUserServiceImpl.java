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
import com.projects.modular.healthPlatform.entity.ActivityUser;
import com.projects.modular.healthPlatform.entity.RegisteredUsers;
import com.projects.modular.healthPlatform.mapper.ActivityUserMapper;
import com.projects.modular.healthPlatform.mapper.RegisteredUsersMapper;
import com.projects.modular.healthPlatform.model.params.ActivityUserParam;
import com.projects.modular.healthPlatform.model.result.ActivityUserResult;
import com.projects.modular.healthPlatform.service.ActivityUserService;

import cn.stylefeng.roses.core.util.ToolUtil;

/**
 * <p>
 * 活动报名人 服务实现类
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
@Service
public class ActivityUserServiceImpl extends ServiceImpl<ActivityUserMapper, ActivityUser> implements ActivityUserService {
	@Autowired
	private RegisteredUsersMapper  registeredUsersMapper;
    @Override
    public void add(ActivityUserParam param){
        ActivityUser entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(ActivityUserParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(ActivityUserParam param){
        ActivityUser oldEntity = getOldEntity(param);
        ActivityUser newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public ActivityUserResult findBySpec(ActivityUserParam param){
        return null;
    }

    @Override
    public List<ActivityUserResult> findListBySpec(ActivityUserParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(ActivityUserParam param){
        Page pageContext = getPageContext();
        QueryWrapper<ActivityUser> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("activity_id", param.getActivityId());
        IPage page = this.page(pageContext, objectQueryWrapper);
        List<ActivityUser> list = page.getRecords();
        List<ActivityUserResult> listResult = new ArrayList<>();
        for (ActivityUser activity : list) {
        	ActivityUserResult result = new ActivityUserResult();
        	ToolUtil.copyProperties(activity, result);
        	RegisteredUsers user = registeredUsersMapper.selectById(activity.getUserId());
        	if(null !=user) {
        		result.setUserName(user.getName());
        	}
        	listResult.add(result);
		}
        page.setRecords(listResult);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(ActivityUserParam param){
        return param.getActivityUserId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private ActivityUser getOldEntity(ActivityUserParam param) {
        return this.getById(getKey(param));
    }

    private ActivityUser getEntity(ActivityUserParam param) {
        ActivityUser entity = new ActivityUser();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
