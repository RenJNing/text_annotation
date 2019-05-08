package com.annotation.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.annotation.mapper.ConnectionMapper;
import com.annotation.mapper.ProjectConnectionMapper;
import com.annotation.pojo.Connection;
import com.annotation.service.ConnectionService;

@Service
public class ConnectionServiceImpl implements ConnectionService {
	@Autowired
	private ConnectionMapper connectionMapper;

	@Autowired
	private ProjectConnectionMapper projectConnectionMapper;

	@Override
	public List<Connection> selectConnectionsByProjectId(Integer projectId) {
		return connectionMapper.selectConnectionsByProjectId(projectId);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public int insertConnection(Map<String, Object> json) {
		@SuppressWarnings("unchecked")
		Map<String, Object> connectionMap = (Map<String, Object>) json.get("connection");
		try {
			Connection connection = new Connection();
			connection.setText((connectionMap.get("text").toString()));
			connectionMapper.insert(connection);
			Map<String, Object> relationMap = new HashMap<>();
			relationMap.put("connectionId", connection.getConnectionId());
			relationMap.put("projectId", json.get("projectId"));
			projectConnectionMapper.insert(relationMap);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public int deleteConnectionById(Integer connectionId) {
		try {
			connectionMapper.deleteByPrimaryKey(connectionId);
			projectConnectionMapper.deleteByConnectionId(connectionId);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public int updateConnection(Connection connection) {
		try {
			connectionMapper.updateByPrimaryKey(connection);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return 0;
		}
		return 1;
	}
}
