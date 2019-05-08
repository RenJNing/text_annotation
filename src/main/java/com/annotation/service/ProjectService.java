package com.annotation.service;

import java.util.HashMap;
import java.util.List;

import com.annotation.pojo.Project;

public interface ProjectService {
	public int insertProject(Project project);

	public List<Project> selectProjects();

	public List<HashMap<String, Object>> exportLabels(Integer projectId);

	public List<HashMap<String, Object>> exportConnections(Integer projectId);
}
