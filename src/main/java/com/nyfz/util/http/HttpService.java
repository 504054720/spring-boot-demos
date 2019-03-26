package com.nyfz.util.http;

import java.util.Map;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component
public class HttpService {
	
	@Autowired
	private CloseableHttpClient httpClient;
	@Autowired
	private RequestConfig requestConfig;
	
	private HttpResult httpResult;
	
	/**
	 * 无参数get方法
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public HttpResult doGet(String url) throws Exception{
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(requestConfig);
		CloseableHttpResponse response = this.httpClient.execute(httpGet);
		return new HttpResult(String.valueOf(response.getStatusLine().getStatusCode()),EntityUtils.toString(response.getEntity(), "UTF-8"));
	}
	/**
	 * 带参数get方法
	 * @param url
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public HttpResult doGet(String url,Map<String, Object> map) throws Exception{
		URIBuilder uriBuilder = new URIBuilder(url);
		if(map != null){
			for(Map.Entry<String, Object> entry : map.entrySet()){
				uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
			}
		}
		return this.doGet(uriBuilder.build().toString());
	}
	/**
	 * 带参数post方法
	 * @param url
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public HttpResult doPost(String url,Map<String, Object> map) throws Exception{
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(requestConfig);
		httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
		if(map != null){
			String params = JSON.toJSONString(map);
			StringEntity entity = new StringEntity(params, "UTF-8");
			entity.setContentType("application/json");
			httpPost.setEntity(entity);
		}
		CloseableHttpResponse response = this.httpClient.execute(httpPost);
		return new HttpResult(String.valueOf(response.getStatusLine().getStatusCode()),EntityUtils.toString(response.getEntity(), "UTF-8"));
	}
	
	

}
