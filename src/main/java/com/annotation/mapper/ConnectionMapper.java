package com.annotation.mapper;

import java.util.List;

import com.annotation.pojo.Connection;

public interface ConnectionMapper {
	int deleteByPrimaryKey(Integer connectionId);

	int insert(Connection record);

	int insertSelective(Connection record);

	Connection selectByPrimaryKey(Integer connectionId);

	int updateByPrimaryKeySelective(Connection record);

	int updateByPrimaryKey(Connection record);

	public List<Connection> selectConnectionsByProjectId(Integer projectId);

}