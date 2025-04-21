
package com.projects.core.common.constant.factory;

import java.util.List;

import com.projects.modular.system.entity.Menu;

/**
 * 常量生产工厂的接口

 */
public interface IConstantFactory {

	/**
	 * 根据用户id获取用户名称

	 */
	String getUserNameById(Long userId);

	/**

	 */
	String getStudentNameById(Long userId);

	/**
	 * 根据用户id获取用户账号
	
	 */
	String getUserAccountById(Long userId);

	/**
	 * 通过角色ids获取角色名称
	 */
	String getRoleName(String roleIds);

	/**
	 * 通过角色id获取角色名称
	 */
	String getSingleRoleName(Long roleId);

	/**
	 * 通过角色id获取角色英文名称
	 */
	String getSingleRoleTip(Long roleId);

	/**
	 * 获取部门名称
	 */
	String getDeptName(Long deptId);

	/**
	 * 获取菜单的名称们(多个)
	 */
	String getMenuNames(String menuIds);

	/**
	 * 获取菜单名称
	 */
	String getMenuName(Long menuId);

	/**
	 * 获取菜单通过编号
	 */
	Menu getMenuByCode(String code);

	/**
	 * 获取菜单名称通过编号
	 */
	String getMenuNameByCode(String code);

	/**
	 * 获取菜单名称通过编号
	 */
	Long getMenuIdByCode(String code);

	/**
	 * 获取字典名称
	 */
	String getDictName(Long dictId);

	/**
	 * 获取年级名称
	 */
	String getGradeName(Long gradeId);

	/**
	 * 获取通知标题
	 */
	String getNoticeTitle(Long dictId);

	/**
	 * 根据字典名称和字典中的值获取对应的名称
	 */
	String getDictsByName(String name, String code);

	/**
	 * 获取性别名称
	 */
	String getSexName(String sexCode);

	/**
	 * 获取用户登录状态
	 */
	String getStatusName(String status);

	/**
	 * 获取菜单状态
	 */
	String getMenuStatusName(String status);


	/**
	 * 获取被缓存的对象(用户删除业务)
	 */
	String getCacheObject(String para);

	/**

	 */
	List<Long> getSubDeptId(Long deptId);

	/**

	 */
	List<Long> getParentDeptIds(Long deptId);



}
