package com.projects.modular.system.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.projects.config.web.WebProperties;
import com.projects.core.beetl.LayuiPageFactory;
import com.projects.core.beetl.MenuNode;
import com.projects.core.common.constant.factory.Const;
import com.projects.core.common.constant.factory.ManagerStatus;
import com.projects.core.common.exception.BizExceptionEnum;
import com.projects.core.shiro.ShiroKit;
import com.projects.core.shiro.ShiroUser;
import com.projects.core.shiro.service.UserAuthService;
import com.projects.modular.system.entity.User;
import com.projects.modular.system.factory.UserFactory;
import com.projects.modular.system.mapper.UserMapper;
import com.projects.modular.system.model.UserDto;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.roses.core.datascope.DataScope;
import cn.stylefeng.roses.core.util.SpringContextHolder;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>

 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

	@Autowired
	private MenuService menuService;

	@Autowired
	private UserAuthService userAuthService;

	/**
	 * 添加用戶
	
	 */
	public void addUser(UserDto user) {

		// 判断账号是否重复
		User theUser = this.getByAccount(user.getAccount());
		if (theUser != null) {
			throw new ServiceException(BizExceptionEnum.USER_ALREADY_REG);
		}

		// 完善账号信息
		String salt = ShiroKit.getRandomSalt(5);
		String password = ShiroKit.md5(user.getPassword(), salt);

		this.save(UserFactory.createUser(user, password, salt));
	}

	/**
	 * 修改用户

	 */
	public void editUser(UserDto user) {
		User oldUser = this.getById(user.getUserId());

		if (ShiroKit.hasRole(Const.ADMIN_NAME)) {
			this.updateById(UserFactory.editUser(user, oldUser));
		} else {
			this.assertAuth(user.getUserId());
			ShiroUser shiroUser = ShiroKit.getUserNotNull();
			if (shiroUser.getId().equals(user.getUserId())) {
				this.updateById(UserFactory.editUser(user, oldUser));
			} else {
				throw new ServiceException(BizExceptionEnum.NO_PERMITION);
			}
		}
	}

	/**
	 * 删除用户
	
	 */
	public void deleteUser(Long userId) {

		// 不能删除超级管理员
		if (userId.equals(Const.ADMIN_ID)) {
			throw new ServiceException(BizExceptionEnum.CANT_DELETE_ADMIN);
		}
		this.assertAuth(userId);
		this.setStatus(userId, ManagerStatus.DELETED.getCode());
	}

	/**
	 * 修改用户状态
	
	 */
	public int setStatus(Long userId, String status) {
		return this.baseMapper.setStatus(userId, status);
	}

	/**
	 * 修改密码

	 */
	public void changePwd(String oldPassword, String newPassword) {
		Long userId = ShiroKit.getUserNotNull().getId();
		User user = this.getById(userId);

		String oldMd5 = ShiroKit.md5(oldPassword, user.getSalt());

		if (user.getPassword().equals(oldMd5)) {
			String newMd5 = ShiroKit.md5(newPassword, user.getSalt());
			user.setPassword(newMd5);
			this.updateById(user);
		} else {
			throw new ServiceException(BizExceptionEnum.OLD_PWD_NOT_RIGHT);
		}
	}

	/**
	 * 根据条件查询用户列表
	
	 */
	public Page<Map<String, Object>> selectUsers(DataScope dataScope, String name, String beginTime, String endTime,
			Long deptId) {
		Page page = LayuiPageFactory.defaultPage();
		return this.baseMapper.selectUsers(page, dataScope, name, beginTime, endTime, deptId);
	}

	/**
	 * 根据条件查询用户列表

	 */
	public Page<Map<String, Object>> selectUsers1(DataScope dataScope, String name, String beginTime, String endTime,
			Long deptId) {
		Page page = LayuiPageFactory.defaultPage();
		return this.baseMapper.selectUsers1(page, dataScope, name, beginTime, endTime, deptId);
	}
	
	
	/**
	 * 设置用户的角色

	 */
	public int setRoles(Long userId, String roleIds) {
		return this.baseMapper.setRoles(userId, roleIds);
	}

	/**
	 * 通过账号获取用户

	 */
	public User getByAccount(String account) {
		return this.baseMapper.getByAccount(account);
	}

	/**
	 * 获取用户菜单列表
	
	 */
	public List<MenuNode> getUserMenuNodes(List<Long> roleList) {
		if (roleList == null || roleList.size() == 0) {
			return new ArrayList<>();
		} else {
			List<MenuNode> menus = menuService.getMenusByRoleIds(roleList);
			List<MenuNode> titles = MenuNode.buildTitle(menus);
			return build(titles);
		}

	}
	 public static List<MenuNode> build(List<MenuNode> nodes) {

	        //如果关闭了接口文档,则不显示接口文档菜单
	        WebProperties webProperties = SpringContextHolder.getBean(WebProperties.class);
	        if (!webProperties.getSwaggerOpen()) {
	            List<MenuNode> menuNodesCopy = new ArrayList<>();
	            for (MenuNode menuNode : nodes) {
	                if (Const.API_MENU_NAME.equals(menuNode.getName())) {
	                    continue;
	                } else {
	                    menuNodesCopy.add(menuNode);
	                }
	            }
	            nodes = menuNodesCopy;
	        }

	        return nodes;
	    }
	/**
	 * 判断当前登录的用户是否有操作这个用户的权限
	
	 */
	public void assertAuth(Long userId) {
		if (ShiroKit.isAdmin()) {
			return;
		}
		List<Long> deptDataScope = ShiroKit.getDeptDataScope();
		User user = this.getById(userId);
		
		if (true) {
			return;
		} else {
			throw new ServiceException(BizExceptionEnum.NO_PERMITION);
		}

	}

	/**
	 * 刷新当前登录用户的信息
	
	 */
	public void refreshCurrentUser() {
		ShiroUser user = ShiroKit.getUserNotNull();
		Long id = user.getId();
		User currentUser = this.getById(id);
		ShiroUser shiroUser = userAuthService.shiroUser(currentUser);
		ShiroUser lastUser = ShiroKit.getUser();
		BeanUtil.copyProperties(shiroUser, lastUser);
	}

	
	public List<User> getList(Long deptId){
		
		  QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    	  queryWrapper.eq("dept_id", deptId);
    	  List<User> list = this.baseMapper.selectList(queryWrapper);
    	  
    	  return list;
	}
	
	
	public List<User> findListBySpec(){
		
	  QueryWrapper<User> queryWrapper = new QueryWrapper<>();
  	  queryWrapper.eq("role_id", 1191180641806200834L);
  	  List<User> list = this.baseMapper.selectList(queryWrapper);
  	  
  	  return list;
	}
	
	
	
}
