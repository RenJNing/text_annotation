package com.annotation.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.annotation.pojo.Project;
import com.annotation.pojo.Sentence;
import com.annotation.service.ProjectService;
import com.annotation.service.SentenceService;
import com.annotation.utils.AjaxResult;
import com.annotation.utils.BaseController;

@RestController
@RequestMapping("/api/manual")
public class ProjectController extends BaseController {
	@Autowired
	private ProjectService projectService;

	@Autowired
	private SentenceService sentenceService;

	/* 查 */
	@RequestMapping("/queryProjectsList")
	public AjaxResult queryProjectsList() {
		return success(projectService.selectProjects());
	}

	/* 导出数据 */
	@RequestMapping("/exportProject")
	public String exportProject(HttpServletRequest request, HttpServletResponse response) {
		try {
			String projectId = request.getParameter("projectId");
			List<HashMap<String, Object>> labelsList = projectService.exportLabels(Integer.valueOf(projectId));
			List<HashMap<String, Object>> connectionsList = projectService
					.exportConnections(Integer.valueOf(projectId));
			/* 下载文件 */
			response.setContentType("application/force-download");// 设置强制下载不打开
			response.addHeader("Content-Disposition",
					"attachment;fileName=" + URLEncoder.encode("export.txt", "UTF-8"));// 设置文件名
			ServletOutputStream outputStream = response.getOutputStream();
			BufferedOutputStream buffer = new BufferedOutputStream(outputStream);
			/* 写入文件 */
			Integer labelIndex = 1;
			Map<Integer, Integer> labelIdMap = new HashMap<>();
			for (HashMap<String, Object> labelsMap : labelsList) {
				String line = "T" + labelIndex + "\t" + labelsMap.get("text") + "\t" + labelsMap.get("start_index")
						+ "\t" + labelsMap.get("end_index") + "\t" + labelsMap.get("content");
				buffer.write(line.getBytes("UTF-8"));
				buffer.write("\r\n".getBytes("UTF-8"));
				labelIdMap.put((Integer) labelsMap.get("label_id"), labelIndex);
				labelIndex++;
			}
			Integer connectionIndex = 1;
			for (HashMap<String, Object> connectionsMap : connectionsList) {
				String line = "R" + connectionIndex + "\t" + connectionsMap.get("text") + "\t" + "Arg1:T"
						+ labelIdMap.get(connectionsMap.get("from_id")) + "\t" + "Arg2:T"
						+ labelIdMap.get(connectionsMap.get("to_id"));
				buffer.write(line.getBytes("UTF-8"));
				buffer.write("\r\n".getBytes("UTF-8"));
				connectionIndex++;
			}
			buffer.flush();
			buffer.close();
			outputStream.close();
			return "下载成功";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "下载失败";
	}

	/* 上传txt */
	@RequestMapping("/upload")
	public AjaxResult upload(@RequestParam("file") MultipartFile file) {
		if (!file.isEmpty()) {
			StringBuffer strBuffer = new StringBuffer();
			String temp = null;
			try {
				/* MultipartFile->File */
				BufferedOutputStream out = new BufferedOutputStream(
						new FileOutputStream(new File(file.getOriginalFilename())));
				out.write(file.getBytes());
				out.flush();
				out.close();
				/* 读取File */
				FileReader fr = new FileReader(file.getOriginalFilename());
				BufferedReader br = new BufferedReader(fr);
				while (null != (temp = br.readLine())) {
					strBuffer.append(temp);
				}
				br.close();
				fr.close();
				/* 删除File */
				File tempFile = new File(file.getOriginalFilename());
				if (tempFile.delete()) {
					System.out.println("tempFile删除成功");
				} else {
					System.out.println("tempFile删除失败");
				}
				/* 插入Project表 */
				Project project = new Project();
				project.setName(file.getOriginalFilename());
				projectService.insertProject(project);
				/* 分句 */
				String[] buff = strBuffer.toString().replaceAll("！", "！;;").replaceAll("!", "!;;")
						.replaceAll("。", "。;;").replaceAll("\\？", "\\？;;").replaceAll("\\?", "\\?;;")
						.replaceAll("\"", "\";;").replaceAll("\ufeff", "").split(";;|\\n");
				Integer startIndex = 0;
				/* 插入Sentence、Project_Sentence表 */
				for (int i = 0; i < buff.length; i++) {
					Sentence sentence = new Sentence();
					sentence.setContent(buff[i]);
					sentence.setStartIndex(startIndex);
					sentence.setLabeled(0);
					sentenceService.insertSentence(sentence, project.getProjectId());
					startIndex += buff[i].length();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return error("上传失败," + e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				return error("上传失败," + e.getMessage());
			}
			return toAjax(true);
		} else {
			return error("上传失败，因为文件是空的.");
		}
	}

}
