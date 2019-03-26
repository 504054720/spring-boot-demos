package com.nyfz.filter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

public class CsrfFilter implements Filter{
	private static final Logger LOGGER = LoggerFactory.getLogger(CsrfFilter.class);
	private List<String> whiteUrls;
	private int wSize;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		whiteUrls = readWhiteUrls(CsrfFilter.class.getResource("csrfWhite.txt").getPath());
		wSize = whiteUrls == null?0 : whiteUrls.size();
		LOGGER.info("CsrfFilter init done!");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String url = httpRequest.getRequestURL().toString();
		String refererUrl =httpRequest.getHeader("Referer");
		if(url.endsWith("login.html")||url.endsWith("home.html") || checkWhite(refererUrl)){
			chain.doFilter(httpRequest, httpResponse);
		}else{
			httpResponse.sendRedirect(httpRequest.getContextPath()+"/login.html");
			return;
		}
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 读取白名单返回LIST
	 * @param fileName
	 * @return
	 */
	public List<String> readWhiteUrls(String fileName){
		List<String> list = new ArrayList<String>();
		FileInputStream fs = null;
		InputStreamReader sr = null;
		BufferedReader br = null;
		
		try {
			File file = new File(fileName);
			fs = new FileInputStream(file);
			sr = new InputStreamReader(fs, "UTF-8");
			br = new BufferedReader(sr);
			String line;
			while((line = br.readLine()) != null){
				if(!"".equals(line))
					list.add(line);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return list;
	}
	/**
	 * 检查refererUrl 是否存在于白名单
	 * @param refererUrl
	 * @return
	 */
	public boolean checkWhite(String refererUrl){
		if(null == refererUrl || "".equals(refererUrl) || wSize == 0){
			return false;
		}
		if(refererUrl.startsWith("http://")){
			refererUrl = refererUrl.substring(7);
		}else if(refererUrl.startsWith("https://")){
			refererUrl = refererUrl.substring(8);
		}
		for(String whiteUrl : whiteUrls){
			if(refererUrl.indexOf(whiteUrl.toLowerCase())>=0){
				return true;
			}
		}
		return false;
	}
	private static  Map<String,String> a = new HashMap<String, String>();
	
	private static final Map<String, String> NUM = a;
	public static void main(String[] args) {
/*		CsrfFilter c = new CsrfFilter();
		c.getA().put("a", "a");
		//System.out.println(c.getNUM().put("a", "a"));
		System.out.println(c.getNUM().get("a"));
		
		c.getA().put("a", "b");
		System.out.println(c.getNUM().get("a"));
		
		c.getNUM().put("a", "c");
		System.out.println(c.getNUM().get("a"));
		*/
		Map<String,String> aa = new HashMap<String, String>();
		a.put("a", "a");
		System.out.println(NUM.get("a"));
		a.put("a", "b");
		System.out.println(NUM.get("a"));
		
		List<String> bigList = new ArrayList<String>();
		for(int i =0;i<555;i++){
			bigList.add("i");
		}
		
		List<List<String>> subLists=new ArrayList<List<String>>();
		final int bigListSize = bigList.size();
		for(int j = 0 ;j<bigListSize;j +=100){
			subLists.add(new ArrayList<String>(bigList.subList(j, Math.min(j+100, bigListSize))));
		}
		
		for(List<String> list : subLists){
			System.out.println(list.size());
		}
 	}


public Map<String, String> getA() {
		return a;
	}

	public void setA(Map<String, String> a) {
		this.a = a;
	}

public Map<String, String> getNUM() {
		return NUM;
	}

public static double getIn(){
	return Math.random();
}
	
}
