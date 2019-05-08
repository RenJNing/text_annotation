package com.annotation.mapper;

import java.util.List;
import java.util.Map;

import com.annotation.pojo.LabelOperate;
import com.annotation.pojo.LabelOperateKey;

public interface LabelOperateMapper {
	int deleteByPrimaryKey(LabelOperateKey key);

	int insert(LabelOperate record);

	int insertSelective(LabelOperate record);

	LabelOperate selectByPrimaryKey(LabelOperateKey key);

	int updateByPrimaryKeySelective(LabelOperate record);

	int updateByPrimaryKey(LabelOperate record);

	public int deleteByProjectIdAndSentenceId(Map<String, Object> record);

	public List<LabelOperate> selectByProjectIdAndSentenceId(Map<String, Object> record);
}