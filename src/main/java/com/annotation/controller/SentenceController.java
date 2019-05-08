package com.annotation.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.annotation.service.SentenceService;
import com.annotation.utils.AjaxResult;
import com.annotation.utils.BaseController;

@RestController
@RequestMapping("/api/manual")
public class SentenceController extends BaseController {
	@Autowired
	private SentenceService sentenceService;

	/* 查 */
	@RequestMapping("/querySentencesList")
	public AjaxResult querySentencesList(@RequestBody Map<String, Object> json) {
		return success(sentenceService.selectSentencesByProjectId((Integer) json.get("projectId")));
	}

	/* 保存标注 */
	@RequestMapping("/saveAnnotation")
	public AjaxResult saveAnnotation(@RequestBody Map<String, Object> json) {
		return toAjax(sentenceService.insertAnnotation(json));
	}

	/* 删除标注 */
	@RequestMapping("/deleteAnnotation")
	public AjaxResult deleteAnnotation(@RequestBody Map<String, Object> json) {
		return toAjax(sentenceService.deleteAnnotation(json));
	}

	/* 查询标注 */
	@RequestMapping("/queryAnnotation")
	public AjaxResult queryAnnotation(@RequestBody Map<String, Object> json) {
		return success(sentenceService.selectAnnotation(json));
	}
}
