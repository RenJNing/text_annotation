package com.annotation.mapper;

import java.util.HashMap;
import java.util.List;

import com.annotation.pojo.Project;

public interface ProjectMapper {
	int deleteByPrimaryKey(Integer projectId);

	int insert(Project record);

	int insertSelective(Project record);

	Project selectByPrimaryKey(Integer projectId);

	int updateByPrimaryKeySelective(Project record);

	int updateByPrimaryKey(Project record);

	public List<Project> selectProjects();

	public List<HashMap<String, Object>> exportLabels(Integer projectId);

	public List<HashMap<String, Object>> exportConnections(Integer projectId);
}