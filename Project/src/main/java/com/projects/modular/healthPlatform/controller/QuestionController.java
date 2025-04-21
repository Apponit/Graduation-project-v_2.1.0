package com.projects.modular.healthPlatform.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.projects.core.beetl.LayuiPageInfo;
import com.projects.core.shiro.ShiroKit;
import com.projects.modular.healthPlatform.entity.Question;
import com.projects.modular.healthPlatform.entity.RegisteredUsers;
import com.projects.modular.healthPlatform.mapper.RegisteredUsersMapper;
import com.projects.modular.healthPlatform.model.params.QuestionParam;
import com.projects.modular.healthPlatform.model.result.QuestionResult;
import com.projects.modular.healthPlatform.service.QuestionService;
import com.projects.modular.system.entity.User;
import com.projects.modular.system.mapper.UserMapper;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;


/**
 * 留言管理控制器
 *
 * @author demo
 * @Date 2025-01-18 16:42:50
 */
@Controller
@RequestMapping("/question")
public class QuestionController extends BaseController {

    private String PREFIX = "/modular/question";

    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RegisteredUsersMapper registeredUsersMapper;

    /**
     * 跳转到主页面
     *
     * @author demo
     * @Date 2025-01-18
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/question.html";
    }

    /**
     * 新增页面
     *
     * @author demo
     * @Date 2025-01-18
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/question_add.html";
    }

    /**
     * 编辑页面
     *
     * @author demo
     * @Date 2025-01-18
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/question_edit.html";
    }

    /**
     * 新增接口
     *
     * @author demo
     * @Date 2025-01-18
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(QuestionParam questionParam) {
    	questionParam.setStatus(0);
    	if(null != questionParam.getPid()) {
    		
    		
    		Question question = questionService.getById(questionParam.getPid());
    		question.setStatus(1);
    		questionService.updateById(question);
    		
    	}else {
    		questionParam.setPid(0L);
    	}
    	
    	questionParam.setCreateTime(new Date());
    	questionParam.setUserId(ShiroKit.getUser().getId());
        this.questionService.add(questionParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author demo
     * @Date 2025-01-18
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(QuestionParam questionParam) {
        this.questionService.update(questionParam);
        return ResponseData.success();
    }

    
    @RequestMapping("/huifu")
    public String huifu() {
        return PREFIX + "/huifu.html";
    }
    
    /**
     * 删除接口
     *
     * @author demo
     * @Date 2025-01-18
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(QuestionParam questionParam) {
        this.questionService.delete(questionParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author demo
     * @Date 2025-01-18
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(QuestionParam questionParam) {
        Question detail = this.questionService.getById(questionParam.getQuestionId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author demo
     * @Date 2025-01-18
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(QuestionParam questionParam) {
        return this.questionService.findPageBySpec(questionParam);
    }

    
    
    @RequestMapping("/getList")
    @ResponseBody
    public ResponseData getList(QuestionParam param) {
    	
    	 QueryWrapper<Question> objectQueryWrapper = new QueryWrapper<>();
         objectQueryWrapper.eq("pid", param.getPid());
         objectQueryWrapper.orderByDesc("create_time");
         List<Question> list = questionService.getBaseMapper().selectList(objectQueryWrapper);
    
         List<QuestionResult>  listResult = new ArrayList<>();
         for (Question question : list) {	 
        	 QuestionResult result = new QuestionResult();
         	ToolUtil.copyProperties(question, result);
         	User user = userMapper.selectById(question.getUserId());
         	if(null != user ) {
         		result.setUserName(user.getName());
         	}else {
         		 RegisteredUsers registeredUsers = registeredUsersMapper.selectById(question.getUserId());
             	if(null != registeredUsers) {
             		result.setUserName(registeredUsers.getName());
             	}
         	}
         	if(question.getUserId().toString().equals(ShiroKit.getUser().getId().toString())) {
         		result.setT(true);
         	}
         	
         	listResult.add(result);
 		}
    	
    
        return ResponseData.success(listResult);
    }
    
    
    
}


