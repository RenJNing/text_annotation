package com.annotation.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.annotation.pojo.Label;
import com.annotation.service.LabelService;
import com.annotation.utils.AjaxResult;
import com.annotation.utils.BaseController;

@RestController
@RequestMapping("/api/manual")
public class LabelController extends BaseController {
	@Autowired
	private LabelService labelService;

	/* 查 */
	@RequestMapping("/queryLabelsList")
	public AjaxResult queryLabelsList(@RequestBody Map<String, Object> json) {
		return success(labelService.selectLabelsByProjectId((Integer) json.get("projectId")));
	}

	/* 增 */
	@RequestMapping("/addLabel")
	public AjaxResult addLabel(@RequestBody Map<String, Object> json) {
		return toAjax(labelService.insertLabel(json));
	}

	/* 删 */
	@RequestMapping("/deleteLabel")
	public AjaxResult deleteLabel(@RequestBody Map<String, Object> json) {
		return toAjax(labelService.deleteLabelById((Integer) json.get("labelId")));
	}

	/* 改 */
	@RequestMapping("/updateLabel")
	public AjaxResult updateLabel(@RequestBody Label label) {
		return toAjax(labelService.updateLabel(label));
	}

}
