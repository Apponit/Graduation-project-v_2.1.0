package com.projects.modular.healthPlatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.projects.core.beetl.LayuiPageInfo;
import com.projects.modular.healthPlatform.entity.ActivityUser;
import com.projects.modular.healthPlatform.model.params.ActivityUserParam;
import com.projects.modular.healthPlatform.service.ActivityUserService;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;


/**
 * 活动报名人控制器
 *
 * @author demo
 * @Date 2025-01-04 22:46:26
 */
@Controller
@RequestMapping("/activityUser")
public class ActivityUserController extends BaseController {

    private String PREFIX = "/modular/activityUser";

    @Autowired
    private ActivityUserService activityUserService;

    /**
     * 跳转到主页面
     *
     * @author demo
     * @Date 2025-01-04
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/activityUser.html";
    }

    /**
     * 新增页面
     *
     * @author demo
     * @Date 2025-01-04
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/activityUser_add.html";
    }

    /**
     * 编辑页面
     *
     * @author demo
     * @Date 2025-01-04
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/activityUser_edit.html";
    }

    /**
     * 新增接口
     *
     * @author demo
     * @Date 2025-01-04
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(ActivityUserParam activityUserParam) {
        this.activityUserService.add(activityUserParam);
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
    public ResponseData editItem(ActivityUserParam activityUserParam) {
        this.activityUserService.update(activityUserParam);
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
    public ResponseData delete(ActivityUserParam activityUserParam) {
        this.activityUserService.delete(activityUserParam);
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
    public ResponseData detail(ActivityUserParam activityUserParam) {
        ActivityUser detail = this.activityUserService.getById(activityUserParam.getActivityUserId());
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
    public LayuiPageInfo list(ActivityUserParam activityUserParam) {
        return this.activityUserService.findPageBySpec(activityUserParam);
    }

}


