package com.projects.modular.healthPlatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.projects.core.beetl.LayuiPageInfo;
import com.projects.modular.healthPlatform.entity.Collect;
import com.projects.modular.healthPlatform.model.params.CollectParam;
import com.projects.modular.healthPlatform.service.CollectService;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;


/**
 * 收藏控制器
 *
 * @author demo
 * @Date 2025-01-18 22:29:32
 */
@Controller
@RequestMapping("/collect")
public class CollectController extends BaseController {

    private String PREFIX = "/modular/collect";

    @Autowired
    private CollectService collectService;

    /**
     * 跳转到主页面
     *
     * @author demo
     * @Date 2025-01-18
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/collect.html";
    }

    /**
     * 新增页面
     *
     * @author demo
     * @Date 2025-01-18
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/collect_add.html";
    }

    /**
     * 编辑页面
     *
     * @author demo
     * @Date 2025-01-18
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/collect_edit.html";
    }

    /**
     * 新增接口
     *
     * @author demo
     * @Date 2025-01-18
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(CollectParam collectParam) {
        this.collectService.add(collectParam);
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
    public ResponseData editItem(CollectParam collectParam) {
        this.collectService.update(collectParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author demo
     * @Date 2025-01-18
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(CollectParam collectParam) {
        this.collectService.delete(collectParam);
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
    public ResponseData detail(CollectParam collectParam) {
        Collect detail = this.collectService.getById(collectParam.getCollectId());
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
    public LayuiPageInfo list(CollectParam collectParam) {
        return this.collectService.findPageBySpec(collectParam);
    }

}


