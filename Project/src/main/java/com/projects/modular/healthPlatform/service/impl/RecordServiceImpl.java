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
import com.projects.modular.healthPlatform.entity.Record;
import com.projects.modular.healthPlatform.entity.RegisteredUsers;
import com.projects.modular.healthPlatform.mapper.RecordMapper;
import com.projects.modular.healthPlatform.mapper.RegisteredUsersMapper;
import com.projects.modular.healthPlatform.model.params.RecordParam;
import com.projects.modular.healthPlatform.model.result.RecordResult;
import  com.projects.modular.healthPlatform.service.RecordService;
import com.projects.modular.system.entity.User;
import com.projects.modular.system.mapper.UserMapper;

import cn.stylefeng.roses.core.util.ToolUtil;

/**
 * <p>
 * 健康档案 服务实现类
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {
	@Autowired
	private UserMapper  userMapper;
	@Autowired
	private RegisteredUsersMapper  registeredUsersMapper;
    @Override
    public void add(RecordParam param){
        Record entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(RecordParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(RecordParam param){
        Record oldEntity = getOldEntity(param);
        Record newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public RecordResult findBySpec(RecordParam param){
        return null;
    }

    @Override
    public List<RecordResult> findListBySpec(RecordParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(RecordParam param){
        Page pageContext = getPageContext();
        QueryWrapper<Record> objectQueryWrapper = new QueryWrapper<>();
        if(ToolUtil.isNotEmpty(param.getUserId())) {
        	objectQueryWrapper.eq("user_id", param.getUserId());
        }
        IPage page = this.page(pageContext, objectQueryWrapper);
        List<Record> records = page.getRecords();
        List<RecordResult> list = new ArrayList<>();
        for (Record record : records) {
        	RecordResult recordResult = new RecordResult();
        	ToolUtil.copyProperties(record, recordResult);
        	User user = userMapper.selectById(record.getOpeId());
        	if(null != user) {
        		recordResult.setOpeName(user.getName());
        	}
        	RegisteredUsers registeredUsers = registeredUsersMapper.selectById(record.getUserId());
        	if(null != registeredUsers) {
        		recordResult.setName(registeredUsers.getName());
        	}
        	list.add(recordResult);
		}
        page.setRecords(list);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(RecordParam param){
        return param.getRecordId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private Record getOldEntity(RecordParam param) {
        return this.getById(getKey(param));
    }

    private Record getEntity(RecordParam param) {
        Record entity = new Record();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
