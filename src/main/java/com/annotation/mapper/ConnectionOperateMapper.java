package com.annotation.mapper;

import java.util.List;
import java.util.Map;

import com.annotation.pojo.ConnectionOperate;
import com.annotation.pojo.ConnectionOperateKey;

public interface ConnectionOperateMapper {
	int deleteByPrimaryKey(ConnectionOperateKey key);

	int insert(ConnectionOperate record);

	int insertSelective(ConnectionOperate record);

	ConnectionOperate selectByPrimaryKey(ConnectionOperateKey key);

	int updateByPrimaryKeySelective(ConnectionOperate record);

	int updateByPrimaryKey(ConnectionOperate record);

	public int deleteByProjectIdAndSentenceId(Map<String, Object> record);

	public List<ConnectionOperate> selectByProjectIdAndSentenceId(Map<String, Object> record);
}