package com.annotation.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.annotation.mapper.UserMapper;
import com.annotation.pojo.User;
import com.annotation.utils.AjaxResult;
import com.annotation.utils.BaseController;

@RestController
@RequestMapping("/api")
public class LoginController extends BaseController {
	@Autowired
	private UserMapper userMapper;

	/**
	 * 登陆
	 *
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 */
	@RequestMapping("/login")
	public AjaxResult login(@RequestBody Map<String, Object> json) {
		String username = (String) json.get("username");
		String password = (String) json.get("password");
		// 从SecurityUtils里边创建一个 subject
		Subject subject = SecurityUtils.getSubject();
		// 在认证提交前准备 token（令牌）
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		// 执行认证登陆
		subject.login(token);
		// 根据权限，指定返回数据
		User user = userMapper.selectByUsername(username);
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("userName", user.getUserName());
		resMap.put("role", user.getRole());
		return success(resMap);
	}

	@RequestMapping("/logout")
	public AjaxResult logout() {
		Subject subject = SecurityUtils.getSubject();
		// 注销
		subject.logout();
		return success("成功注销！");
	}

}
