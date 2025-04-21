

package com.projects.core.beetl;

import static cn.stylefeng.roses.core.util.HttpContext.getRequest;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.DisabledAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import cn.stylefeng.roses.kernel.model.exception.ServiceException;

import com.projects.config.web.ErrorResponseData;
import com.projects.core.common.exception.AccountLogedException;
import com.projects.core.common.exception.BizExceptionEnum;


/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）

 */
@ControllerAdvice
@Order(-1)
public class GlobalExceptionHandler {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 拦截业务异常
	 */
	@ExceptionHandler(ServiceException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorResponseData bussiness(ServiceException e) {
	
		return new ErrorResponseData(e.getCode(), e.getMessage());
	}

	/**
	 * 用户未登录异常
	 */
	@ExceptionHandler(AuthenticationException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public String unAuth(AuthenticationException e, Model model) {
		log.error("用户未登陆：", e);
		String username = getRequest().getParameter("username");
		//LogManager.me().executeLog(LogTaskFactory.loginLog(username, "账号在另一个地方登录", getIp()));
		model.addAttribute("tips", "重启服务在试试");
		return "/login.html";
	}

	/**
	 * 账号在另一个地方登录
	 */
	@ExceptionHandler(AccountLogedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public String accountLoged(AccountLogedException e, Model model) {
	
		return "/login.html";
	}

	/**
	 * 账号被冻结异常
	 */
	@ExceptionHandler(DisabledAccountException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public String accountLocked(DisabledAccountException e, Model model) {
		String username = getRequest().getParameter("username");
	
		model.addAttribute("tips", "账号被冻结");
		return "/login.html";
	}

	/**
	 * 账号密码错误异常
	 */
	@ExceptionHandler(CredentialsException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public String credentials(CredentialsException e, Model model) {
		String username = getRequest().getParameter("username");
	
		model.addAttribute("tips", "账号密码错误");
		return "/login.html";
	}



	/**
	 * 无权访问该资源异常
	 */
	@ExceptionHandler(UndeclaredThrowableException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public ErrorResponseData credentials(UndeclaredThrowableException e) {
		getRequest().setAttribute("tip", "权限异常");
		log.error("权限异常!", e);
		return new ErrorResponseData(BizExceptionEnum.NO_PERMITION.getCode(),
				BizExceptionEnum.NO_PERMITION.getMessage());
	}

	/**
	 * 拦截未知的运行时异常
	 */
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorResponseData notFount(RuntimeException e) {
	
		getRequest().setAttribute("tip", "服务器未知运行时异常");
		log.error("运行时异常:", e);
		return new ErrorResponseData(BizExceptionEnum.SERVER_ERROR.getCode(),
				BizExceptionEnum.SERVER_ERROR.getMessage());
	}

	@ExceptionHandler(BindException.class)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ErrorResponseData bindExceptionHandler(BindException ex) {
		StringBuffer sb = new StringBuffer("参数：");
		if (ex.hasErrors()) {
			List<FieldError> list = ex.getFieldErrors();

			for (FieldError error : list) {
				sb.append(error.getField()).append("-");
				sb.append(error.getField()).append(error.getDefaultMessage() + "         ");

			}
			return new ErrorResponseData(300, sb.toString());

		}
		return new ErrorResponseData(300, sb.toString());
	}

	/**
	 * 捕捉数据异常
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value = ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ErrorResponseData ConstraintViolationExceptionHandler(ConstraintViolationException ex) {
		StringBuffer sb = new StringBuffer("参数：");
		Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
		Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();

		while (iterator.hasNext()) {
			ConstraintViolation<?> cvl = iterator.next();
			sb.append(cvl.getMessageTemplate());
		}

		return new ErrorResponseData(300, sb.toString());

	}

}
