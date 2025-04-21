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
import com.projects.modular.healthPlatform.entity.Comment;
import com.projects.modular.healthPlatform.entity.RegisteredUsers;
import com.projects.modular.healthPlatform.mapper.CommentMapper;
import com.projects.modular.healthPlatform.mapper.RegisteredUsersMapper;
import com.projects.modular.healthPlatform.model.params.CommentParam;
import com.projects.modular.healthPlatform.model.result.ActivityUserResult;
import com.projects.modular.healthPlatform.model.result.CommentResult;
import com.projects.modular.healthPlatform.service.CommentService;

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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
	@Autowired
	private RegisteredUsersMapper  registeredUsersMapper ;
    @Override
    public void add(CommentParam param){
        Comment entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(CommentParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(CommentParam param){
        Comment oldEntity = getOldEntity(param);
        Comment newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public CommentResult findBySpec(CommentParam param){
        return null;
    }

    @Override
    public List<CommentResult> findListBySpec(CommentParam param){
    	   QueryWrapper<Comment> objectQueryWrapper = new QueryWrapper<>();
    	   objectQueryWrapper.eq("forum_id", param.getForumId());
    	   objectQueryWrapper.orderByDesc("create_time");
    	   List<Comment> list = baseMapper.selectList(objectQueryWrapper);
    	   List<CommentResult>  list1 = new ArrayList<CommentResult>();
    	   for (Comment comment : list) {
    		   CommentResult result = new CommentResult();
    		   ToolUtil.copyProperties(comment, result);
    		   RegisteredUsers registeredUsers = registeredUsersMapper.selectById(comment.getUserId());
  			 result.setNickName(registeredUsers.getName());
  			 result.setHead(registeredUsers.getPic());
  			list1.add(result);
		}
        return list1;
    }

    @Override
    public LayuiPageInfo findPageBySpec(CommentParam param){
        Page pageContext = getPageContext();
        QueryWrapper<Comment> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("forum_id", param.getForumId());
        IPage page = this.page(pageContext, objectQueryWrapper);
        List<Comment> list = page.getRecords();
        List<CommentResult> listResult = new ArrayList<>();
        for (Comment activity : list) {
        	CommentResult result = new CommentResult();
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

    private Serializable getKey(CommentParam param){
        return param.getCommentId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private Comment getOldEntity(CommentParam param) {
        return this.getById(getKey(param));
    }

    private Comment getEntity(CommentParam param) {
        Comment entity = new Comment();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
