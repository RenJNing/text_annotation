package com.annotation.service;

import java.util.List;
import java.util.Map;

import com.annotation.pojo.User;

public interface UserService {
	public List<User> selectUsers();

	public User selectByUsername(String userName);

	public int insert(User record);

	public int updateByPrimaryKeySelective(Map<String, Object> json);
}
