package com.annotation.service;

import java.util.List;
import java.util.Map;

import com.annotation.pojo.Connection;

public interface ConnectionService {
	public List<Connection> selectConnectionsByProjectId(Integer projectId);

	public int insertConnection(Map<String, Object> json);

	public int deleteConnectionById(Integer connectionId);

	public int updateConnection(Connection connection);
}
