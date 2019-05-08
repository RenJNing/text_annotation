package com.annotation.mapper;

import java.util.List;

import com.annotation.pojo.Sentence;

public interface SentenceMapper {
	int deleteByPrimaryKey(Integer sentenceId);

	int insert(Sentence record);

	int insertSelective(Sentence record);

	Sentence selectByPrimaryKey(Integer sentenceId);

	int updateByPrimaryKeySelective(Sentence record);

	int updateByPrimaryKey(Sentence record);

	public List<Sentence> selectSentencesByProjectId(Integer projectId);
}