package com.annotation.controller;

import org.apache.shiro.authc.AccountException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.annotation.utils.AjaxResult;
import com.annotation.utils.BaseController;

@RestControllerAdvice
public class ExceptionController extends BaseController {
	// 捕捉 CustomRealm 抛出的异常
	@ExceptionHandler(AccountException.class)
	public AjaxResult handleShiroException(Exception ex) {
		return error(ex.getMessage());
	}
}
