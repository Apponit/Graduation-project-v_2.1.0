package com.projects.modular.healthPlatform.controller;

import java.util.List;

import cn.stylefeng.roses.core.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.projects.core.beetl.LayuiPageInfo;
import com.projects.modular.healthPlatform.entity.RegisteredUsers;
import com.projects.modular.healthPlatform.model.params.RegisteredUsersParam;
import com.projects.modular.healthPlatform.service.RegisteredUsersService;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;


/**
 * 注册用户控制器
 *
 * @author demo
 * @Date 2025-01-04 11:43:44
 */
@Controller
@RequestMapping("/registeredUsers")
public class RegisteredUsersController extends BaseController {

    private String PREFIX = "/modular/registeredUsers";

    @Autowired
    private RegisteredUsersService registeredUsersService;

    /** 
     * 跳转到主页面
     *
     * @author demo
     * @Date 2025-01-04
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/registeredUsers.html";
    }

    /**
     * 新增页面
     *
     * @author demo
     * @Date 2025-01-04
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/registeredUsers_add.html";
    }

    /**
     * 编辑页面
     *
     * @author demo
     * @Date 2025-01-04
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/registeredUsers_edit.html";
    }

    /**
     * 新增接口
     *
     * @author demo
     * @Date 2025-01-04
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(RegisteredUsersParam registeredUsersParam) {


        String string = MD5Util.encrypt(registeredUsersParam.getPassWord());
        registeredUsersParam.setPassWord(string);

        this.registeredUsersService.add(registeredUsersParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author demo
     * @Date 2025-01-04
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(RegisteredUsersParam registeredUsersParam) {
        this.registeredUsersService.update(registeredUsersParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author demo
     * @Date 2025-01-04
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(RegisteredUsersParam registeredUsersParam) {
        this.registeredUsersService.delete(registeredUsersParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author demo
     * @Date 2025-01-04
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(RegisteredUsersParam registeredUsersParam) {
        RegisteredUsers detail = this.registeredUsersService.getById(registeredUsersParam.getUserId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author demo
     * @Date 2025-01-04
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(RegisteredUsersParam registeredUsersParam) {
        return this.registeredUsersService.findPageBySpec(registeredUsersParam);
    }

    
    
    
    @RequestMapping("/getList")
    @ResponseBody
    public ResponseData getList() {
    	  QueryWrapper<RegisteredUsers> objectQueryWrapper = new QueryWrapper<>();
    	  List<RegisteredUsers> list = registeredUsersService.getBaseMapper().selectList(objectQueryWrapper);
        return ResponseData.success(list);
    }
    
    
    
    
    
}


