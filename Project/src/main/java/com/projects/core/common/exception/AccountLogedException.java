
package com.projects.core.common.exception;

import cn.stylefeng.roses.kernel.model.exception.AbstractBaseExceptionEnum;

/**
 * 验证码错误异常

 */
public class AccountLogedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7064685820233712527L;

	public AccountLogedException(AbstractBaseExceptionEnum exception) {
		super(exception.getMessage());
	}

}
