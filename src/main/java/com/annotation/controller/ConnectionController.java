package com.annotation.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.annotation.pojo.Connection;
import com.annotation.service.ConnectionService;
import com.annotation.utils.AjaxResult;
import com.annotation.utils.BaseController;

@RestController
@RequestMapping("/api/manual")
public class ConnectionController extends BaseController {
	@Autowired
	private ConnectionService connectionService;

	/* 查 */
	@RequestMapping("/queryConnectionsList")
	public AjaxResult queryConnectionsList(@RequestBody Map<String, Object> json) {
		return success(connectionService.selectConnectionsByProjectId((Integer) json.get("projectId")));
	}

	/* 增 */
	@RequestMapping("/addConnection")
	public AjaxResult addLabel(@RequestBody Map<String, Object> json) {
		return toAjax(connectionService.insertConnection(json));
	}

	/* 删 */
	@RequestMapping("/deleteConnection")
	public AjaxResult deleteConnection(@RequestBody Map<String, Object> json) {
		return toAjax(connectionService.deleteConnectionById(((Integer) json.get("connectionId"))));
	}

	/* 改 */
	@RequestMapping("/updateConnection")
	public AjaxResult updateConnection(@RequestBody Connection connection) {
		return toAjax(connectionService.updateConnection(connection));
	}
}
