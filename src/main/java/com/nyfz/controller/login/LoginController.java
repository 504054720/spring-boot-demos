package com.nyfz.controller.login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.nyfz.util.http.HttpResult;
import com.nyfz.util.http.HttpService;




@Controller
@RequestMapping("/admin")
public class LoginController {
	@Autowired
	private HttpService httpService;

	@GetMapping("/login")
	//@CachePut(value="redisCache",key="111")
	public String login(HttpSession session){
		/*System.out.println("=======================setuserkey");
		 session.setAttribute("userkey", "1");*/
		
		
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
			List<Map<String,String>> listMap = (List<Map<String, String>>) JSON.parseObject(result2.getBody()).get("userlist");

			
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
			contentMap.put("msgtype", "text");
			contentMap.put("safe", 1);
			Map<String,String> cont = new HashMap<String, String>();
			
			for(Map m : listMap){
				//一对一设置发送内容并发送
				contentMap.put("touser", m.get("userid"));
				cont.put("content", contentMaps.get(m.get("name")));
				contentMap.put("text", cont);
				HttpResult re=httpService.doPost(sendMesUrl, contentMap);
				System.out.println("userid:"+m.get("userid"));
				System.out.println("name:"+m.get("name"));
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

	
}
