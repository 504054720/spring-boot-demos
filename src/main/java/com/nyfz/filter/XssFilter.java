package com.nyfz.filter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XssFilter implements Filter{
	private static final Logger  LOGGER = LoggerFactory.getLogger(XssFilter.class);
	private static final String MU_CONTENT_TYPE = "multipart/form-data";
	/**
	 * 排除部分URL不做过滤
	 */
	private List<String> excludeUrls = new ArrayList<String>();
	private List<String> excludeRealUrls = new ArrayList<String>();
	private Map<String,String> paramMap = new HashMap<String, String>();

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		response.addHeader("x-frame-options", "SAMEORIGIN");
		String pathInfo = request.getPathInfo() == null ? "" : request.getPathInfo();
		String url = request.getServletPath() + pathInfo;
		String uri = request.getRequestURI();
		String contentType = request.getContentType();
		LOGGER.info("contentType:"+contentType + "---URL:"+url+"---URI:"+uri);
		
		if(excludeUrls.contains(url) && ((!contentType.isEmpty() && contentType.toLowerCase().indexOf(MU_CONTENT_TYPE)>=0) 
				|| uri.toLowerCase().indexOf("upload")>=0)){
			LOGGER.info("上传、富文本框等连接不做处理："+url);
			arg2.doFilter(arg0, arg1);
			return;
			
		}
		Enumeration<String> params = request.getParameterNames();
		while(params.hasMoreElements()){
			String nextElement = params.nextElement();
			String elementValue = request.getParameter(nextElement);
			//校验是否存在SQL注入信息
			String tipStr = checkSql(elementValue, url);
			if(!tipStr.isEmpty()){
				errorResponse(response, tipStr);
				return;
			}
			
		}
		arg2.doFilter(arg0, arg1);
		return;
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		excludeUrls = readFile(XssFilter.class.getResource("xssWhite.txt").getPath());
		excludeRealUrls = readFile(XssFilter.class.getResource("realWhite.txt").getPath());
		paramMap.put("cntty", "cntty");
		
	}
	/**
	 * 读取白名单
	 */
	private List<String> readFile(String fileName){
		List<String> list = new ArrayList<String>();
		FileInputStream fs = null;
		InputStreamReader sr = null;
		BufferedReader br = null;
		
		try {
			File file = new File(fileName);
			if(file.isFile()&&file.exists()){
				fs = new FileInputStream(file);
				sr = new InputStreamReader(fs,"UTF-8");
				br = new BufferedReader(sr);
				String line;
				while((line=br.readLine()) != null ){
					if(!"".equals(line))
					list.add(line);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(br != null)
					br.close();
				if(sr != null)
					sr.close();
				if(fs != null)
					fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	private void errorResponse(HttpServletResponse response,String tipStr) throws IOException{
		String warning = "您输入的内容包含以下非法字符："+tipStr;
		response.setContentType("text/html:charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		out.print("<script type=\"text/javascript\">");
		out.print("alert("+warning+");");
		out.print("history.go(-1);");
		out.print("</script>");
		out.flush();
		out.close();
	}
	
	public static String checkSql(String str,String url){
		String retMsg = "";
		if(str == null || "".equals(str.trim())){//空字符串不存在非法字符
			return retMsg;
		}
		String[] checkStr = {"script","mid","master","truncate","insert","select","update","delete",
				"declare","iframe","onreadystatechange","alert","atestu","xss",
				":","<",">","(",")","\\","svg","confirm","prompt","onload","onmouseover",
				"onfocus","onerror","and ","or ","union ","where ","limit ","group ","by ","hex",
				"substr","\r","\n"};
		String strTmp = str.toLowerCase();
		for(String cStr : checkStr){
			if(strTmp.indexOf(cStr)>=0){
				LOGGER.info("xss防攻击拦截URL："+url+"，原因：特殊字符，传入str="+strTmp+"，包含特殊字符："+cStr);
				if("\r".equals(cStr) || "\n".equals(cStr))
					retMsg += "|[换行符]";
				else
					retMsg += "|["+cStr+"]";
			}
		}
			
		return retMsg;
	}

}
