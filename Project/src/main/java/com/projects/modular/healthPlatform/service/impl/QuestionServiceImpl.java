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
import com.projects.modular.healthPlatform.entity.Question;
import com.projects.modular.healthPlatform.entity.RegisteredUsers;
import com.projects.modular.healthPlatform.mapper.QuestionMapper;
import com.projects.modular.healthPlatform.mapper.RegisteredUsersMapper;
import com.projects.modular.healthPlatform.model.params.QuestionParam;
import com.projects.modular.healthPlatform.model.result.QuestionResult;
import com.projects.modular.healthPlatform.service.QuestionService;

import cn.stylefeng.roses.core.util.ToolUtil;

/**
 * <p>
 * 留言管理 服务实现类
 * </p>
 *
 * @author demo
 * @since 2025-01-18
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {
	@Autowired
	private RegisteredUsersMapper  registeredUsersMapper;
    @Override
    public void add(QuestionParam param){
        Question entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(QuestionParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(QuestionParam param){
        Question oldEntity = getOldEntity(param);
        Question newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public QuestionResult findBySpec(QuestionParam param){
        return null;
    }

    @Override
    public List<QuestionResult> findListBySpec(QuestionParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(QuestionParam param){
        Page pageContext = getPageContext();
        QueryWrapper<Question> objectQueryWrapper = new QueryWrapper<>();     
        objectQueryWrapper.eq("pid", 0);
        if(ToolUtil.isNotEmpty(param.getContext())) {
        	objectQueryWrapper.like("context", param.getContext());
        }
        IPage page = this.page(pageContext, objectQueryWrapper);
        List<Question> records = page.getRecords();
        List<QuestionResult>  list = new ArrayList<>();
        for (Question question : records) {
        	QuestionResult result = new QuestionResult();
        	ToolUtil.copyProperties(question, result);
        	 RegisteredUsers registeredUsers = registeredUsersMapper.selectById(question.getUserId());
        	if(null != registeredUsers) {
        		result.setUserName(registeredUsers.getName());
        	}
        	list.add(result);
		}
        page.setRecords(list);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(QuestionParam param){
        return param.getQuestionId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private Question getOldEntity(QuestionParam param) {
        return this.getById(getKey(param));
    }

    private Question getEntity(QuestionParam param) {
        Question entity = new Question();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
