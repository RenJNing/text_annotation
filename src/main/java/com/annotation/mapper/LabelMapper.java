package com.annotation.mapper;

import java.util.List;

import com.annotation.pojo.Label;

public interface LabelMapper {
	int deleteByPrimaryKey(Integer labelId);

	int insert(Label record);

	int insertSelective(Label record);

	Label selectByPrimaryKey(Integer labelId);

	int updateByPrimaryKeySelective(Label record);

	int updateByPrimaryKey(Label record);

	public List<Label> selectLabelsByProjectId(Integer projectId);
}