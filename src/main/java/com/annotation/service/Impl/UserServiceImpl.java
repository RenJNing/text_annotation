package com.annotation.service.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.annotation.mapper.UserMapper;
import com.annotation.pojo.User;
import com.annotation.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;

	@Override
	public List<User> selectUsers() {
		return userMapper.selectUsers();
	}

	@Override
	public User selectByUsername(String userName) {
		return userMapper.selectByUsername(userName);
	}

	@Override
	public int insert(User record) {
		return userMapper.insert(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Map<String, Object> json) {
		Integer userId = (Integer) json.get("userId");
		String role = (String) json.get("role");
		User record = new User();
		record.setUserId(userId);
		record.setRole(role);
		return userMapper.updateByPrimaryKeySelective(record);
	}

}
