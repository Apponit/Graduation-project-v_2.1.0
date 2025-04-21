
package com.projects.modular.system.controller;

import java.util.List;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.projects.core.beetl.MenuNode;
import com.projects.core.shiro.ShiroKit;
import com.projects.core.shiro.ShiroUser;
import com.projects.modular.system.entity.User;
import com.projects.modular.system.factory.UserFactory;
import com.projects.modular.system.model.UserDto;
import com.projects.modular.system.service.UserService;

import cn.stylefeng.roses.core.base.controller.BaseController;

/**
 * 登录控制器
 */
@Controller
public class LoginController extends BaseController {

	@Autowired
	private UserService userService;

	/**
	 * 跳转到主页
	
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {

		// 获取当前用户角色列表
		ShiroUser user = ShiroKit.getUserNotNull();
		List<Long> roleList = user.getRoleList();

		if (roleList == null || roleList.size() == 0) {
			ShiroKit.getSubject().logout();
			model.addAttribute("tips", "该用户没有角色，无法登陆");
			return "/login.html";
		}

		List<MenuNode> menus = userService.getUserMenuNodes(roleList);
		model.addAttribute("menus", menus);

		return "/index.html";
	}

	/**
	 * 跳转到登录页面
	 *

	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		if (ShiroKit.isAuthenticated() || ShiroKit.getUser() != null) {
			return REDIRECT + "/";
		} else {
			return "/login.html";
		}
	}

	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register() {
		
		return "/register.html";
		
	}
	
	
	/**
	 * 点击登录执行的动作
	 *
	
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerVali(Model model) {

		String username = super.getPara("username").trim();
		String name = super.getPara("name").trim();
		String phone = super.getPara("phone");
		String email = super.getPara("email");
		String password = super.getPara("password");
		
		User byAccount = userService.getByAccount(username);
		
		if(null !=byAccount) {
			model.addAttribute("tips", "该用户已注册");
			return "/register.html";
		}

		
		
		UserDto user = new UserDto();
		user.setAccount(username);
		user.setEmail(email);
		user.setName(name);
		user.setPhone(phone);	
		user.setPassword(password);	
		// 完善账号信息
		String salt = ShiroKit.getRandomSalt(5);
		String password1 = ShiroKit.md5(user.getPassword(), salt);
		userService.save(UserFactory.createUser(user, password1, salt));
		model.addAttribute("tips", "注册成功");
		return "/login.html";
	}

	
	
	@Autowired
	private SessionDAO sessionDAO;

	/**
	 * 点击登录执行的动作
	 *
	
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginVali() {

		String username = super.getPara("username").trim();
		String password = super.getPara("password").trim();
		String remember = super.getPara("remember");

		UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray());

		// 如果开启了记住我功能
		if ("on".equals(remember)) {
			token.setRememberMe(true);
		} else {
			token.setRememberMe(false);
		}

		// 执行shiro登录操作
		Subject currentUser = ShiroKit.getSubject();
		currentUser.login(token);
		// 登录成功，记录登录日志
		ShiroUser shiroUser = ShiroKit.getUserNotNull();
		

		ShiroKit.getSession().setAttribute("sessionFlag", true);

		return REDIRECT + "/";
	}

	/**
	 * 退出登录
	 *
	 
	
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logOut() {
	
		ShiroKit.getSubject().getSession().removeAttribute("loginedUser");
		ShiroKit.getSubject().logout();
		deleteAllCookie();
		return REDIRECT + "/login";
	}
}
