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
import com.projects.modular.healthPlatform.entity.HealthyService;
import com.projects.modular.healthPlatform.entity.Order;
import com.projects.modular.healthPlatform.entity.RegisteredUsers;
import com.projects.modular.healthPlatform.mapper.HealthyServiceMapper;
import com.projects.modular.healthPlatform.mapper.OrderMapper;
import com.projects.modular.healthPlatform.mapper.RegisteredUsersMapper;
import com.projects.modular.healthPlatform.model.params.OrderParam;
import com.projects.modular.healthPlatform.model.result.OrderResult;
import  com.projects.modular.healthPlatform.service.OrderService;

import cn.stylefeng.roses.core.util.ToolUtil;

/**
 * <p>
 * 服务预约 服务实现类
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
	@Autowired
	private HealthyServiceMapper  healthyServiceMapper;
	@Autowired
	private RegisteredUsersMapper  registeredUsersMapper;
	
    @Override
    public void add(OrderParam param){
        Order entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(OrderParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(OrderParam param){
        Order oldEntity = getOldEntity(param);
        Order newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public OrderResult findBySpec(OrderParam param){
        return null;
    }

    @Override
    public List<OrderResult> findListBySpec(OrderParam param){
    	QueryWrapper<Order> objectQueryWrapper = new QueryWrapper<>();
  	  objectQueryWrapper.eq("user_id", param.getUserId());
  	objectQueryWrapper.orderByDesc("time");
  	  
  	List<Order> list = baseMapper.selectList(objectQueryWrapper);
  	List<OrderResult>  listResult = new ArrayList<>();
  	for (Order order : list) {
  		OrderResult result = new OrderResult();
  		ToolUtil.copyProperties(order, result);
  		HealthyService healthyService = healthyServiceMapper.selectById(order.getServiceId());
  		if(null !=healthyService) {
  			result.setPic(healthyService.getCover());
  			result.setName(healthyService.getName());
  			result.setTel(healthyService.getTel());
  		}
  	
  		  listResult.add(result);
		}
  	
  	
      return listResult;
  }

    @Override
    public LayuiPageInfo findPageBySpec(OrderParam param){
        Page pageContext = getPageContext();
        QueryWrapper<Order> objectQueryWrapper = new QueryWrapper<>();
        if(ToolUtil.isNotEmpty(param.getUserId())) {
        	objectQueryWrapper.eq("user_id", param.getUserId());
        }
        IPage page = this.page(pageContext, objectQueryWrapper);
        
        List<Order> list = page.getRecords();
        List<OrderResult> listResult = new ArrayList<>();
        for (Order order : list) {
        	OrderResult result = new OrderResult();
        	ToolUtil.copyProperties(order, result);
        	HealthyService healthyService = healthyServiceMapper.selectById(order.getServiceId());
      		if(null !=healthyService) {
      			result.setPic(healthyService.getCover());
      			result.setName(healthyService.getName());
      		
      		}
      	
      		RegisteredUsers registeredUsers = registeredUsersMapper.selectById(order.getUserId());
      		if(null !=registeredUsers) {
      			result.setUserName(registeredUsers.getName());
      			result.setTel(registeredUsers.getAddress());
      		}
      		
      		 
        	listResult.add(result);
		}
        page.setRecords(listResult);
        
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(OrderParam param){
        return param.getOrderId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private Order getOldEntity(OrderParam param) {
        return this.getById(getKey(param));
    }

    private Order getEntity(OrderParam param) {
        Order entity = new Order();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
