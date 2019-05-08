package com.annotation.mapper;

import com.annotation.pojo.ProjectSentenceKey;

public interface ProjectSentenceMapper {
    int deleteByPrimaryKey(ProjectSentenceKey key);

    int insert(ProjectSentenceKey record);

    int insertSelective(ProjectSentenceKey record);
}