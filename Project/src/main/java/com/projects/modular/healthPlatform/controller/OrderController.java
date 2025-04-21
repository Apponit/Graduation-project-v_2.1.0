package com.projects.modular.healthPlatform.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.projects.core.beetl.LayuiPageInfo;
import com.projects.modular.healthPlatform.entity.Location;
import com.projects.modular.healthPlatform.entity.Order;
import com.projects.modular.healthPlatform.entity.RegisteredUsers;
import com.projects.modular.healthPlatform.model.params.OrderParam;
import com.projects.modular.healthPlatform.service.LocationService;
import com.projects.modular.healthPlatform.service.OrderService;
import com.projects.modular.healthPlatform.service.RegisteredUsersService;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;


/**
 * 服务预约控制器
 *
 * @author demo
 * @Date 2025-01-04 23:22:04
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

    private String PREFIX = "/modular/order";
    @Autowired
    private RegisteredUsersService registeredUsersService;
    @Autowired
    private  OrderService orderService;
    @Autowired
    private LocationService locationService;

    /**
     * 跳转到主页面
     *
     * @author demo
     * @Date 2025-01-04
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/order.html";
    }

    /**
     * 新增页面
     *
     * @author demo
     * @Date 2025-01-04
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/order_add.html";
    }

    /**
     * 编辑页面
     *
     * @author demo
     * @Date 2025-01-04
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/order_edit.html";
    }

    /**
     * 新增接口
     *
     * @author demo
     * @Date 2025-01-04
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(OrderParam orderParam) {
        this.orderService.add(orderParam);
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
    public ResponseData editItem(OrderParam orderParam) {
        this.orderService.update(orderParam);
        return ResponseData.success();
    }

    
    
    
    
    
  
    
    
    /**
     * 图表统计
  * @param model
  * @return
  */
 @RequestMapping("/statistics")
 public String statistics(Model model) {
 	
 	
 	  QueryWrapper<RegisteredUsers> objectQueryWrapper = new QueryWrapper<>();
 	 
 	   Integer count3 = registeredUsersService.getBaseMapper().selectCount(objectQueryWrapper);
 	   	
 	   model.addAttribute("price", count3);
 	   
 	   QueryWrapper<Location> patientWrapper = new QueryWrapper<>();
 	
 	   Integer count = locationService.getBaseMapper().selectCount(patientWrapper);
 	 
 	   model.addAttribute("profit", count);      	   
 	   QueryWrapper<Order> objectQueryWrapper1 = new QueryWrapper<>();
 	   objectQueryWrapper1.ge("time", new SimpleDateFormat("yyyy-MM-dd").format(new Date())+" 00:00:00");
 	   objectQueryWrapper1.le("time", new SimpleDateFormat("yyyy-MM-dd").format(new Date())+" 23:59:59");
 	 
 	    Integer selectCount = orderService.getBaseMapper().selectCount(objectQueryWrapper1);
 	 
 	
 	   
 	   model.addAttribute("price1", selectCount); 
 	   
 	   
 	   
 	   QueryWrapper<Location> patientWrapper1 = new QueryWrapper<>();
 	   patientWrapper1.ge("time", new SimpleDateFormat("yyyy-MM-dd").format(new Date())+" 00:00:00");
 	   patientWrapper1.le("time", new SimpleDateFormat("yyyy-MM-dd").format(new Date())+" 23:59:59");
 	   Integer count1 = locationService.getBaseMapper().selectCount(patientWrapper1);   
 	   model.addAttribute("profit1", count1);
 	
 	   List<String>  days = new ArrayList<>();
 	   List<String>  days1 = new ArrayList<>();
 	   List<String>  priceList = new ArrayList<>();
 	   List<String>  profitList = new ArrayList<>();
 	   for (int i = 6; i >= 1; i--) {
 		   Calendar c = Calendar.getInstance();
     	   c.setTime(new Date());
            c.add(Calendar.DATE, - i);
            Date d = c.getTime();
            String day = new SimpleDateFormat("yyyy-MM-dd").format(d);
            days.add(day);
            days1.add(new SimpleDateFormat("MM-dd").format(d));
		 }
 	   days.add(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
 	   days1.add(new SimpleDateFormat("MM-dd").format(new Date()));
 	   List<String>  zhouOrder = new ArrayList<>();
 	   List<String>  zhouOrder1 = new ArrayList<>();
 	   for (String string : days) {
 		   QueryWrapper<Order> objectQueryWrapper2= new QueryWrapper<>();
 		   objectQueryWrapper2.ge("time", string+" 00:00:00");
 		   objectQueryWrapper2.le("time", string+" 23:59:59");  	 
 	
     	   Integer count2 = orderService.getBaseMapper().selectCount(objectQueryWrapper2);
     	   priceList.add(count2+"");
    
     	
     	   QueryWrapper<Location> objectQueryWrapper3= new QueryWrapper<>();
     	   objectQueryWrapper3.ge("time", string+" 00:00:00");
     	   objectQueryWrapper3.le("time", string+" 23:59:59");
     	   
     	   Integer count4 = locationService.getBaseMapper().selectCount(objectQueryWrapper3);
     	  
     	   
     	   profitList.add(count4.toString());
     	 
 	   }
 		 
 	   
 	   model.addAttribute("zhouOrder", zhouOrder); 
 	   model.addAttribute("zhouOrder1", zhouOrder1); 
 
 	   model.addAttribute("days", days1);
 	   model.addAttribute("priceList", priceList);
 	   //model.addAttribute("priceList", Joiner.on(",").join(priceList));
 	   model.addAttribute("profitList", profitList);
 	   
 	   
 	   
 	   
 	   String[] moth = new String[] {"2025-01","2025-02","2025-03","2025-04","2025-05","2025-06","2025-07","2025-08","2025-09","2025-10","2025-11","2025-12"};
 	   List<String>  yearOrder = new ArrayList<>();
 	   List<String>  yearOrder1 = new ArrayList<>();
 	 
 	   model.addAttribute("yearOrderList", yearOrder);
 	   model.addAttribute("yearOrder1List", yearOrder1);
 	   model.addAttribute("moth", moth);
 	   
 	   
 	  List<String>  yearOrder11 = new ArrayList<>();
	  
	   for (int i = 0; i < moth.length; i++) {
		   QueryWrapper<Order> objectQueryWrapper10 = new QueryWrapper<>();
		   objectQueryWrapper10.apply("DATE_FORMAT(time,'%Y-%m') = '"+moth[i]+"'");
		   Integer count2 = orderService.getBaseMapper().selectCount(objectQueryWrapper10);
		         	
   	   
   	       yearOrder11.add(count2.toString());
   	 
	   }
	   model.addAttribute("yearOrderprice", yearOrder11);

 
 	   
 	   
 	   
     return PREFIX + "/statistics.html";
 }

    
    
    
    
    
    
    
    
    
    
    /**
     * 删除接口
     *
     * @author demo
     * @Date 2025-01-04
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(OrderParam orderParam) {
        this.orderService.delete(orderParam);
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
    public ResponseData detail(OrderParam orderParam) {
        Order detail = this.orderService.getById(orderParam.getOrderId());
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
    public LayuiPageInfo list(OrderParam orderParam) {
        return this.orderService.findPageBySpec(orderParam);
    }

}


