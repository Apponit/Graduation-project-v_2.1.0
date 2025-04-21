package com.projects.modular.healthPlatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.projects.core.beetl.LayuiPageInfo;
import com.projects.core.shiro.ShiroKit;
import com.projects.modular.healthPlatform.entity.Record;
import com.projects.modular.healthPlatform.model.params.RecordParam;
import com.projects.modular.healthPlatform.service.RecordService;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;


/**
 * 健康档案控制器
 *
 * @author demo
 * @Date 2025-01-04 23:22:04
 */
@Controller
@RequestMapping("/record")
public class RecordController extends BaseController {

    private String PREFIX = "/modular/record";

    @Autowired
    private RecordService recordService;

    /**
     * 跳转到主页面
     *
     * @author demo
     * @Date 2025-01-04
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/record.html";
    }

    /**
     * 新增页面
     *
     * @author demo
     * @Date 2025-01-04
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/record_add.html";
    }

    /**
     * 编辑页面
     *
     * @author demo
     * @Date 2025-01-04
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/record_edit.html";
    }

    /**
     * 新增接口
     *
     * @author demo
     * @Date 2025-01-04
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(RecordParam recordParam) {
    	recordParam.setOpeId(ShiroKit.getUser().getId());
        this.recordService.add(recordParam);
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
    public ResponseData editItem(RecordParam recordParam) {
    	recordParam.setOpeId(ShiroKit.getUser().getId());
        this.recordService.update(recordParam);
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
    public ResponseData delete(RecordParam recordParam) {
        this.recordService.delete(recordParam);
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
    public ResponseData detail(RecordParam recordParam) {
        Record detail = this.recordService.getById(recordParam.getRecordId());
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
    public LayuiPageInfo list(RecordParam recordParam) {
        return this.recordService.findPageBySpec(recordParam);
    }

}


