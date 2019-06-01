package com.annotation.mapper;

import java.util.List;

import com.annotation.pojo.User;

public interface UserMapper {
	int deleteByPrimaryKey(Integer userId);

	int insert(User record);

	int insertSelective(User record);

	User selectByPrimaryKey(Integer userId);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);

	User selectByUsername(String userName);

	public List<User> selectUsers();
}