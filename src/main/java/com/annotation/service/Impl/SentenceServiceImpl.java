package com.annotation.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.annotation.mapper.ConnectionOperateMapper;
import com.annotation.mapper.LabelOperateMapper;
import com.annotation.mapper.ProjectSentenceMapper;
import com.annotation.mapper.SentenceMapper;
import com.annotation.pojo.ConnectionOperate;
import com.annotation.pojo.LabelOperate;
import com.annotation.pojo.ProjectSentenceKey;
import com.annotation.pojo.Sentence;
import com.annotation.service.SentenceService;

@Service
public class SentenceServiceImpl implements SentenceService {
	@Autowired
	private SentenceMapper sentenceMapper;

	@Autowired
	private ProjectSentenceMapper projectSentenceMapper;

	@Autowired
	private LabelOperateMapper labelOperateMapper;

	@Autowired
	private ConnectionOperateMapper connectionOperateMapper;

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public int insertSentence(Sentence sentence, Integer projectId) {
		try {
			sentenceMapper.insert(sentence);
			ProjectSentenceKey record = new ProjectSentenceKey();
			record.setProjectId(projectId);
			record.setSentenceId(sentence.getSentenceId());
			projectSentenceMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return 0;
		}
		return 1;
	}

	@Override
	public List<Sentence> selectSentencesByProjectId(Integer projectId) {
		return sentenceMapper.selectSentencesByProjectId(projectId);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public int insertAnnotation(Map<String, Object> json) {
		try {
			/* 清空label_operate表与connection_operate表 */
			deleteAnnotation(json);
			/* 插入label_operate表 */
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> labelsList = (List<Map<String, Object>>) json.get("labels");
			Map<Integer, Integer> labelIdMap = new HashMap<>();
			for (Map<String, Object> labelsmap : labelsList) {
				LabelOperate labelOperate = new LabelOperate();
				labelOperate.setCategoryId((Integer) labelsmap.get("categoryId"));
				labelOperate.setEndIndex((Integer) labelsmap.get("endIndex"));
				labelOperate.setProjectId((Integer) json.get("projectId"));
				labelOperate.setSentenceId((Integer) json.get("sentenceId"));
				labelOperate.setStartIndex((Integer) labelsmap.get("startIndex"));
				labelOperateMapper.insert(labelOperate);
				labelIdMap.put((Integer) labelsmap.get("id"), labelOperate.getLabelId());
			}
			/* 插入connection_operate表 */
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> connectionssList = (List<Map<String, Object>>) json.get("connections");
			for (Map<String, Object> connectionsmap : connectionssList) {
				ConnectionOperate connectionOperate = new ConnectionOperate();
				connectionOperate.setCategoryId((Integer) connectionsmap.get("categoryId"));
				connectionOperate.setFromId(labelIdMap.get(connectionsmap.get("fromId")));
				connectionOperate.setToId(labelIdMap.get(connectionsmap.get("toId")));
				connectionOperate.setProjectId((Integer) json.get("projectId"));
				connectionOperate.setSentenceId((Integer) json.get("sentenceId"));
				connectionOperateMapper.insert(connectionOperate);
			}
			/* 插入sentence表 记录已标注 */
			Sentence sentence = new Sentence();
			sentence.setSentenceId((Integer) json.get("sentenceId"));
			sentence.setLabeled(1);
			sentenceMapper.updateByPrimaryKeySelective(sentence);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public int deleteAnnotation(Map<String, Object> json) {
		try {
			labelOperateMapper.deleteByProjectIdAndSentenceId(json);
			connectionOperateMapper.deleteByProjectIdAndSentenceId(json);
			/* 插入sentence表 清空已标注 */
			Sentence sentence = new Sentence();
			sentence.setSentenceId((Integer) json.get("sentenceId"));
			sentence.setLabeled(0);
			sentenceMapper.updateByPrimaryKeySelective(sentence);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return 0;
		}
		return 1;
	}

	@Override
	public Map<String, Object> selectAnnotation(Map<String, Object> json) {
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("projectId", json.get("projectId"));
		responseMap.put("sentenceId", json.get("sentenceId"));
		responseMap.put("labels", labelOperateMapper.selectByProjectIdAndSentenceId(json));
		responseMap.put("connections", connectionOperateMapper.selectByProjectIdAndSentenceId(json));
		return responseMap;
	}

}
