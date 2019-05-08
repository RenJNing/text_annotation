package com.annotation.service;

import java.util.List;
import java.util.Map;

import com.annotation.pojo.Sentence;

public interface SentenceService {
	public int insertSentence(Sentence sentence, Integer projectId);

	public List<Sentence> selectSentencesByProjectId(Integer projectId);

	public int insertAnnotation(Map<String, Object> json);

	public int deleteAnnotation(Map<String, Object> json);

	public Map<String, Object> selectAnnotation(Map<String, Object> json);
}
