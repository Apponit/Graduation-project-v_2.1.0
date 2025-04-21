/**
 * 
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.projects.core.common.constant.factory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.projects.modular.system.entity.Menu;
import com.projects.modular.system.entity.Role;
import com.projects.modular.system.entity.User;
import com.projects.modular.system.mapper.MenuMapper;
import com.projects.modular.system.mapper.RoleMapper;
import com.projects.modular.system.mapper.UserMapper;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.core.util.SpringContextHolder;
import cn.stylefeng.roses.core.util.ToolUtil;

/**
 * 常量的生产工厂

 */
@Component
@DependsOn("springContextHolder")
public class ConstantFactory implements IConstantFactory {

	private RoleMapper roleMapper = SpringContextHolder.getBean(RoleMapper.class);
	
	
	private UserMapper userMapper = SpringContextHolder.getBean(UserMapper.class);
	private MenuMapper menuMapper = SpringContextHolder.getBean(MenuMapper.class);
	
	
	

	public static IConstantFactory me() {
		return SpringContextHolder.getBean("constantFactory");
	}

	@Override
	public String getUserNameById(Long userId) {
		User user = userMapper.selectById(userId);
		if (user != null) {
			return user.getName();
		} else {
			return "--";
		}
	}

	

	@Override
	public String getUserAccountById(Long userId) {
		User user = userMapper.selectById(userId);
		if (user != null) {
			return user.getAccount();
		} else {
			return "--";
		}
	}

	@Override
	@Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.ROLES_NAME + "'+#roleIds")
	public String getRoleName(String roleIds) {
		if (ToolUtil.isEmpty(roleIds)) {
			return "";
		}
		Long[] roles = Convert.toLongArray(roleIds);
		StringBuilder sb = new StringBuilder();
		for (Long role : roles) {
			Role roleObj = roleMapper.selectById(role);
			if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName())) {
				sb.append(roleObj.getName()).append(",");
			}
		}
		return StrUtil.removeSuffix(sb.toString(), ",");
	}

	@Override
	@Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.SINGLE_ROLE_NAME + "'+#roleId")
	public String getSingleRoleName(Long roleId) {
		if (0 == roleId) {
			return "--";
		}
		Role roleObj = roleMapper.selectById(roleId);
		if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName())) {
			return roleObj.getName();
		}
		return "";
	}

	@Override
	@Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.SINGLE_ROLE_TIP + "'+#roleId")
	public String getSingleRoleTip(Long roleId) {
		if (0 == roleId) {
			return "--";
		}
		Role roleObj = roleMapper.selectById(roleId);
		if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName())) {
			return roleObj.getDescription();
		}
		return "";
	}

	@Override
	@Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.DEPT_NAME + "'+#deptId")
	public String getDeptName(Long deptId) {
		if (deptId == null) {
			return "";
		} else if (deptId == 0L) {
			return "顶级";
		} else {
			
			return "";
		}
	}

	@Override
	public String getMenuNames(String menuIds) {
		Long[] menus = Convert.toLongArray(menuIds);
		StringBuilder sb = new StringBuilder();
		for (Long menu : menus) {
			Menu menuObj = menuMapper.selectById(menu);
			if (ToolUtil.isNotEmpty(menuObj) && ToolUtil.isNotEmpty(menuObj.getName())) {
				sb.append(menuObj.getName()).append(",");
			}
		}
		return StrUtil.removeSuffix(sb.toString(), ",");
	}

	@Override
	public String getMenuName(Long menuId) {
		if (ToolUtil.isEmpty(menuId)) {
			return "";
		} else {
			Menu menu = menuMapper.selectById(menuId);
			if (menu == null) {
				return "";
			} else {
				return menu.getName();
			}
		}
	}

	@Override
	public Menu getMenuByCode(String code) {
		if (ToolUtil.isEmpty(code)) {
			return new Menu();
		} else if (code.equals("0")) {
			return new Menu();
		} else {
			Menu param = new Menu();
			param.setCode(code);
			QueryWrapper<Menu> queryWrapper = new QueryWrapper<>(param);
			Menu menu = menuMapper.selectOne(queryWrapper);
			if (menu == null) {
				return new Menu();
			} else {
				return menu;
			}
		}
	}

	@Override
	public String getMenuNameByCode(String code) {
		if (ToolUtil.isEmpty(code)) {
			return "";
		} else if (code.equals("0")) {
			return "顶级";
		} else {
			Menu param = new Menu();
			param.setCode(code);
			QueryWrapper<Menu> queryWrapper = new QueryWrapper<>(param);
			Menu menu = menuMapper.selectOne(queryWrapper);
			if (menu == null) {
				return "";
			} else {
				return menu.getName();
			}
		}
	}

	@Override
	public Long getMenuIdByCode(String code) {
		if (ToolUtil.isEmpty(code)) {
			return 0L;
		} else if (code.equals("0")) {
			return 0L;
		} else {
			Menu menu = new Menu();
			menu.setCode(code);
			QueryWrapper<Menu> queryWrapper = new QueryWrapper<>(menu);
			Menu tempMenu = this.menuMapper.selectOne(queryWrapper);
			return tempMenu.getMenuId();
		}
	}

	@Override
	public String getDictName(Long dictId) {
		return "";
	}

	

	

	


	@Override
	public String getDictsByName(String name, String code) {
		return "";
	}

	@Override
	public String getSexName(String sexCode) {
		return getDictsByName("性别", sexCode);
	}

	@Override
	public String getStatusName(String status) {
		return ManagerStatus.getDescription(status);
	}

	@Override
	public String getMenuStatusName(String status) {
		return MenuStatus.getDescription(status);
	}

	
	@Override
	public String getCacheObject(String para) {
		return "";
	}

	@Override
	public List<Long> getSubDeptId(Long deptId) {
		ArrayList<Long> deptIds = new ArrayList<>();

		

			return deptIds;
		
	}

	@Override
	public List<Long> getParentDeptIds(Long deptId) {
		
		ArrayList<Long> parentDeptIds = new ArrayList<>();
		
		return parentDeptIds;
	}

	@Override
	public String getStudentNameById(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNoticeTitle(Long dictId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getGradeName(Long gradeId) {
		// TODO Auto-generated method stub
		return null;
	}

	




	
}
