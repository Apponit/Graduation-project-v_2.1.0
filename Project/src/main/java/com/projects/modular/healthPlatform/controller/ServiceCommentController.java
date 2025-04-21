package com.projects.modular.healthPlatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.projects.core.beetl.LayuiPageInfo;
import com.projects.modular.healthPlatform.entity.ServiceComment;
import com.projects.modular.healthPlatform.model.params.ServiceCommentParam;
import com.projects.modular.healthPlatform.service.ServiceCommentService;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;


/**
 * 评论控制器
 *
 * @author demo
 * @Date 2025-01-04 23:22:04
 */
@Controller
@RequestMapping("/serviceComment")
public class ServiceCommentController extends BaseController {

    private String PREFIX = "/modular/serviceComment";

    @Autowired
    private ServiceCommentService serviceCommentService;

    /**
     * 跳转到主页面
     *
     * @author demo
     * @Date 2025-01-04
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/serviceComment.html";
    }

    /**
     * 新增页面
     *
     * @author demo
     * @Date 2025-01-04
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/serviceComment_add.html";
    }

    /**
     * 编辑页面
     *
     * @author demo
     * @Date 2025-01-04
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/serviceComment_edit.html";
    }

    /**
     * 新增接口
     *
     * @author demo
     * @Date 2025-01-04
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(ServiceCommentParam serviceCommentParam) {
        this.serviceCommentService.add(serviceCommentParam);
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
    public ResponseData editItem(ServiceCommentParam serviceCommentParam) {
        this.serviceCommentService.update(serviceCommentParam);
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
    public ResponseData delete(ServiceCommentParam serviceCommentParam) {
        this.serviceCommentService.delete(serviceCommentParam);
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
    public ResponseData detail(ServiceCommentParam serviceCommentParam) {
        ServiceComment detail = this.serviceCommentService.getById(serviceCommentParam.getCommentId());
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
    public LayuiPageInfo list(ServiceCommentParam serviceCommentParam) {
        return this.serviceCommentService.findPageBySpec(serviceCommentParam);
    }

}


