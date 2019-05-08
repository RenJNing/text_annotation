package com.annotation.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.annotation.mapper.LabelMapper;
import com.annotation.mapper.ProjectLabelMapper;
import com.annotation.pojo.Label;
import com.annotation.service.LabelService;

@Service
public class LabelServiceImpl implements LabelService {
	@Autowired
	private LabelMapper labelMapper;

	@Autowired
	private ProjectLabelMapper projectLabelMapper;

	@Override
	public List<Label> selectLabelsByProjectId(Integer projectId) {
		return labelMapper.selectLabelsByProjectId(projectId);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public int insertLabel(Map<String, Object> json) {
		@SuppressWarnings("unchecked")
		Map<String, Object> labelMap = (Map<String, Object>) json.get("label");
		try {
			Label label = new Label();
			label.setText((labelMap.get("text").toString()));
			label.setColor(labelMap.get("color").toString());
			label.setBordercolor(labelMap.get("bordercolor").toString());
			labelMapper.insert(label);
			Map<String, Object> relationMap = new HashMap<>();
			relationMap.put("labelId", label.getLabelId());
			relationMap.put("projectId", json.get("projectId"));
			projectLabelMapper.insert(relationMap);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public int deleteLabelById(Integer labelId) {
		try {
			labelMapper.deleteByPrimaryKey(labelId);
			projectLabelMapper.deleteByLabelId(labelId);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public int updateLabel(Label label) {
		try {
			labelMapper.updateByPrimaryKey(label);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return 0;
		}
		return 1;
	}

}
