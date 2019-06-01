package com.annotation.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.annotation.service.UserService;
import com.annotation.utils.AjaxResult;
import com.annotation.utils.BaseController;

@RestController
@RequestMapping("/api")
public class UserController extends BaseController {
	@Autowired
	private UserService userService;

	@RequestMapping("/queryUsersList")
	public AjaxResult queryUsersList() {
		return success(userService.selectUsers());
	}

	@RequestMapping("/assignRole")
	public AjaxResult assignRole(@RequestBody Map<String, Object> json) {
		return success(userService.updateByPrimaryKeySelective(json));
	}
}
