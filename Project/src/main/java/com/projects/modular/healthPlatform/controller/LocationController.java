package com.projects.modular.healthPlatform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.projects.core.beetl.LayuiPageInfo;
import com.projects.modular.healthPlatform.entity.Location;
import com.projects.modular.healthPlatform.entity.RegisteredUsers;
import com.projects.modular.healthPlatform.model.params.LocationParam;
import com.projects.modular.healthPlatform.model.result.ResultData;
import com.projects.modular.healthPlatform.service.LocationService;
import com.projects.modular.healthPlatform.service.RegisteredUsersService;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;


/**
 * 一键求救控制器
 *
 * @author demo
 * @Date 2025-01-04 23:22:04
 */
@Controller
@RequestMapping("/location")
public class LocationController extends BaseController {

    private String PREFIX = "/modular/location";

    @Autowired
    private LocationService locationService;
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
        return PREFIX + "/location.html";
    }

    /**
     * 新增页面
     *
     * @author demo
     * @Date 2025-01-04
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/location_add.html";
    }

    /**
     * 编辑页面
     *
     * @author demo
     * @Date 2025-01-04
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/location_edit.html";
    }

    /**
     * 新增接口
     *
     * @author demo
     * @Date 2025-01-04
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(LocationParam locationParam) {
        this.locationService.add(locationParam);
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
    public ResponseData editItem(LocationParam locationParam) {
        this.locationService.update(locationParam);
        return ResponseData.success();
    }


    @RequestMapping("/map")
    public String map(Model model, String lat, String lng) {

        model.addAttribute("lat", lat);
        model.addAttribute("lng", lng);
        return PREFIX + "/map.html";
    }


    /**
     * 删除接口
     *
     * @author demo
     * @Date 2025-01-04
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(LocationParam locationParam) {
        this.locationService.delete(locationParam);
        return ResponseData.success();
    }


    @RequestMapping("/getLocationList")
    @ResponseBody
    public ResponseData getLocationList() {
        QueryWrapper<Location> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("status", 0);
        List<Location> list = locationService.getBaseMapper().selectList(objectQueryWrapper);
        String name = "";
        ResultData resultData = new ResultData();
        if (null != list && !list.isEmpty()) {
            int listSize = list.size() - 1;
            Location location = list.get(0);
            RegisteredUsers registeredUsers = registeredUsersService.getById(location.getUserId());
            //name+="姓名："+registeredUsers.getName()+"紧急呼救请速度前往紧急救援，可请先尝试播打电话与对方进行联系，联系电话："+registeredUsers.getAddress()+"。还有"+listSize+"条信息未处理！";

            name += "姓名：" + registeredUsers.getName() + "紧急呼救请速度前往紧急救援，可请先尝试播打电话与对方进行联系，联系电话：" + registeredUsers.getAddress();
            // 如果列表中还有其他未处理信息，添加剩余信息数提示
            if (listSize > 0) {
                name += "。还有" + listSize + "条信息未处理！";
            }

            location.setStatus(1);
            locationService.updateById(location);
            resultData.setName(name);
            resultData.setLat(location.getLat());
            resultData.setLng(location.getLng());
        }


        return ResponseData.success(resultData);
    }

    /**
     * 查看详情接口
     *
     * @author demo
     * @Date 2025-01-04
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(LocationParam locationParam) {
        Location detail = this.locationService.getById(locationParam.getLocationId());
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
    public LayuiPageInfo list(LocationParam locationParam) {
        return this.locationService.findPageBySpec(locationParam);
    }

}


