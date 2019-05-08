package com.annotation.service;

import java.util.List;
import java.util.Map;

import com.annotation.pojo.Label;

public interface LabelService {

	public List<Label> selectLabelsByProjectId(Integer projectId);

	public int insertLabel(Map<String, Object> json);

	public int deleteLabelById(Integer labelId);

	public int updateLabel(Label label);

}
