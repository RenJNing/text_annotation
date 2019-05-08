package com.annotation.mapper;

import java.util.Map;

import com.annotation.pojo.ProjectLabelKey;

public interface ProjectLabelMapper {
	int deleteByPrimaryKey(ProjectLabelKey key);

	int deleteByLabelId(Integer labelId);

	int insert(Map<String, Object> record);

	int insertSelective(ProjectLabelKey record);
}