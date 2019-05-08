package com.annotation.service.Impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.annotation.mapper.ProjectMapper;
import com.annotation.pojo.Project;
import com.annotation.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {
	@Autowired
	private ProjectMapper projectMapper;

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public int insertProject(Project project) {
		try {
			projectMapper.insert(project);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return 0;
		}
		return 1;
	}

	@Override
	public List<Project> selectProjects() {
		return projectMapper.selectProjects();
	}

	@Override
	public List<HashMap<String, Object>> exportLabels(Integer projectId) {
		return projectMapper.exportLabels(projectId);
	}

	@Override
	public List<HashMap<String, Object>> exportConnections(Integer projectId) {
		return projectMapper.exportConnections(projectId);
	}

}
