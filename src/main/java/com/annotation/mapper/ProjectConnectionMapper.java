package com.annotation.mapper;

import java.util.Map;

import com.annotation.pojo.ProjectConnectionKey;

public interface ProjectConnectionMapper {
	int deleteByPrimaryKey(ProjectConnectionKey key);

	int insert(Map<String, Object> record);

	int insertSelective(ProjectConnectionKey record);

	public int deleteByConnectionId(Integer connectionId);
}