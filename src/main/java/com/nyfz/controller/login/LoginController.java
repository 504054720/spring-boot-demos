package com.nyfz.controller.login;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.nyfz.util.http.HttpResult;
import com.nyfz.util.http.HttpService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


@Controller
@RequestMapping("/admin")
public class LoginController {
	@Autowired
	private HttpService httpService;

	@PostMapping("/login")
	@ResponseBody
	public String login( @RequestParam("file") MultipartFile file){
		/*System.out.println("=======================setuserkey");
		 session.setAttribute("userkey", "1");*/

		if(file.isEmpty()){
			return "文件不可为空！";
		}


		System.out.println("fileName:"+file.getOriginalFilename());
		String fileName = file.getOriginalFilename();
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		InputStream inputStream;
		Workbook workbook = null;
		try {
			inputStream = file.getInputStream();
			if(".xlsx".equals(suffix)){
				workbook = new XSSFWorkbook(inputStream);
			}else if(".xls".equals(suffix)){
				workbook = new HSSFWorkbook(inputStream);
			}else {
				return "请上传.xlsx或.xls格式文档！";
			}
		} catch (IOException e) {
			return "上传异常";
		}

		//获取名称、行号对应关系
		Map<String,List> relationMap = getRelationMap(workbook);
		try {





			//获取通讯录token
			String getAddressTokenUrl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ww403d6245519a4750&corpsecret=-8n4Nv7Xw1m0t6RDQUkbpq6TQkAV5m5nPXgGyOeE8IA";
			Map<String,String> map = new HashMap<String, String>();
			HttpResult result = httpService.doGet(getAddressTokenUrl);
			//HttpResult re=httpService.doPost("http://localhost:8082/nyfz/post", map);
			System.out.println("result_code:"+result.getCode());
			System.out.println("result_body:"+result.getBody());
			Map<String,String> tokenMap = (Map<String, String>) JSON.parse(result.getBody());
			String addressToken = tokenMap.get("access_token");
			
			//获取通讯录列表
			String getAddressListUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist?access_token="+addressToken+"&department_id=1&fetch_child=1";
			HttpResult result2 = httpService.doGet(getAddressListUrl);
			System.out.println("result2_body:"+result2.getBody());
			List<Map<String,String>> userListMap = (List<Map<String, String>>) JSON.parseObject(result2.getBody()).get("userlist");



			//获取发送消息token
			String getSendTokenUrl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ww403d6245519a4750&corpsecret=xI2eU5r8OoXVu3JR3tpeaS-CwFSc2Pq5YWAUkhHPidE";
			HttpResult result3 = httpService.doGet(getSendTokenUrl);
			System.out.println("result3_body:"+result3.getBody());
			String sendToken = (String) JSON.parseObject(result3.getBody()).get("access_token");

			
			//组装内容集合
			Map<String,String> contentMaps = new HashMap<String, String>();
			contentMaps.put("曹俊景", "这是发给曹俊景的内容");
			
			//遍历通讯录
			String sendMesUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="+sendToken;
			Map<String,Object> contentMap = new HashMap<String, Object>();
			contentMap.put("agentid", 1000003);
			//contentMap.put("msgtype", "text");
			contentMap.put("msgtype", "markdown");
			//contentMap.put("safe", 1);
			Map<String,String> cont = new HashMap<String, String>();
			
			for(Map user : userListMap){
				//一对一设置发送内容并发送
				contentMap.put("touser", user.get("userid"));
				Workbook perWorkbook = new XSSFWorkbook();
				Sheet sheet = perWorkbook.createSheet();
				//sheet.setArrayFormula()


				//cont.put("content", contentMaps.get(user.get("name")));
				cont.put("content", getContent(workbook.getSheetAt(0).getRow(0), relationMap,workbook,user.get("name").toString()));

				//contentMap.put("text", cont);
				contentMap.put("markdown", cont);
				HttpResult re=httpService.doPost(sendMesUrl, contentMap);
				System.out.println("userid:"+user.get("userid"));
				System.out.println("name:"+user.get("name"));
				System.out.println("sendMessageResult:"+re.getBody());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "login";
		
	}
	
	@GetMapping("/info")
	public String info(){
		return "404";
		
	}
	@GetMapping("/user/{id}")
	public String user(Model model,@PathVariable String id){
		//model.addAttribute("user", "tom"+id);
		return "user";
	}

	public Map<String,List> getRelationMap(Workbook workbook){
		Map<String,List> relationMap = new HashMap<String,List>();

		Sheet sheet = workbook.getSheetAt(0);
		int rowNum = 0;
		Row headRow = sheet.getRow(0);
		List rowList = null;
		for(Row row : sheet){
			if(rowNum == 0){
				rowNum++;
				continue;
			}else {
				Cell cell = row.getCell(0);
				System.out.println(cell.getStringCellValue());

				if (relationMap.get(cell.getStringCellValue()) == null) {
					rowList = new ArrayList<>();
					rowList.add(rowNum);

				} else {
					rowList = relationMap.get(cell.getStringCellValue());
					rowList.add(rowNum);
				}
				relationMap.put(cell.getStringCellValue(),rowList);
			}
			rowNum++;

		}
		return relationMap;
	}
	public String getContent(Row headRow,Map<String,List> relationMap,Workbook contentWorkbook,String userName){
		StringBuilder content = new StringBuilder();
		List<Integer> rowList = relationMap.get(userName);

		for(int in=0;in<headRow.getLastCellNum();in++){
			Cell cell = headRow.getCell(in);
			System.out.println(cell.getStringCellValue());
			content.append(">**"+cell.getStringCellValue()+":** ");


			if (rowList != null) {
				for(int i =0 ;i<rowList.size();i++){
					Row oldRow = contentWorkbook.getSheetAt(0).getRow(rowList.get(i));//获取内容行
					if (1 == oldRow.getCell(in).getCellType()) {
						content.append("  "+oldRow.getCell(in).getStringCellValue());
					} else {
						content.append("  "+oldRow.getCell(in).getNumericCellValue());
					}

//					for (Cell cellt : oldRow) {
//						System.out.println(cellt.getCellType());
//
//						if(1 == cellt.getCellType()){
//							content.append(cellt.getStringCellValue());
//						}else {
//							content.append(cellt.getNumericCellValue());
//						}
//
//					}
				}
			}
			content.append("\n");


		}


		return content.toString();
	}

	
}
