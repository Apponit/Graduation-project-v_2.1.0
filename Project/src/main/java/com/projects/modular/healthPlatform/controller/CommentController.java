package com.projects.modular.healthPlatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.projects.core.beetl.LayuiPageInfo;
import com.projects.modular.healthPlatform.entity.Comment;
import com.projects.modular.healthPlatform.model.params.CommentParam;
import com.projects.modular.healthPlatform.service.CommentService;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;


/**
 * 评论控制器
 *
 * @author demo
 * @Date 2025-01-04 22:03:46
 */
@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController {

    private String PREFIX = "/modular/comment";
 
    @Autowired
    private CommentService commentService;

    /**
     * 跳转到主页面
     *
     * @author demo
     * @Date 2025-01-04
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/comment.html";
    }

    /**
     * 新增页面
     *
     * @author demo
     * @Date 2025-01-04
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/comment_add.html";
    }

    /**
     * 编辑页面
     *
     * @author demo
     * @Date 2025-01-04
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/comment_edit.html";
    }

    /**
     * 新增接口
     *
     * @author demo
     * @Date 2025-01-04
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(CommentParam commentParam) {
        this.commentService.add(commentParam);
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
    public ResponseData editItem(CommentParam commentParam) {
        this.commentService.update(commentParam);
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
    public ResponseData delete(CommentParam commentParam) {
        this.commentService.delete(commentParam);
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
    public ResponseData detail(CommentParam commentParam) {
        Comment detail = this.commentService.getById(commentParam.getCommentId());
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
    public LayuiPageInfo list(CommentParam commentParam) {
        return this.commentService.findPageBySpec(commentParam);
    }

}


