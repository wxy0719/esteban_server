package com.esteban.core.framework.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpClientUtil {
	private static Log LOG = LogFactory.getLog(HttpClientUtil.class);
	
	public static JSON httpGet(String url){
		JSON jsonResult = null;
		try{
			CloseableHttpClient httpClient = HttpClients.createDefault();
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(180000).build();
			HttpGet getMethod = new HttpGet(url);
			getMethod.setConfig(requestConfig);
			HttpResponse response = httpClient.execute(getMethod);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				String stringResult = EntityUtils.toString(response.getEntity());
				jsonResult = (JSON) JSON.parse(stringResult);
				url = URLDecoder.decode(url, "utf-8");
			} else {
				LOG.error("get 请求提交失败：" + url);
			}
		} catch (Exception e) {
			LOG.error("get 请求提交失败：" + url);
		}
		return jsonResult;
	}
	
	public static JSONObject httpPost(String url, JSONObject jsonParam, boolean needResponse){
		JSONObject jsonResult = null;
		try{
			CloseableHttpClient httpClient = HttpClients.createDefault();
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(180000).build();
			HttpPost postMethod = new HttpPost(url);
			postMethod.setConfig(requestConfig);
			if(null != jsonParam){
				StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
				entity.setContentEncoding("utf-8");
				entity.setContentType("application/json");
				postMethod.setEntity(entity);
			}
			HttpResponse response = httpClient.execute(postMethod);
			url = URLDecoder.decode(url, "utf-8");
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				String str = "";
				str = EntityUtils.toString(response.getEntity());
				if(!needResponse){
					return null;
				}
				jsonResult = JSONObject.parseObject(str);
			} else {
				LOG.error("post 请求提交失败：" + url);
			}
		} catch (Exception e) {
			LOG.error("post 请求提交失败：" + url);
		}
		return jsonResult;
	}
	
	public static JSONObject httpPost(String url, JSONObject jsonParam){
		return httpPost(url, jsonParam, true);
	}
	
	/**
	 * 适用于不支持json参数请求的接口
	 * @param url
	 * @param params
	 * @param needResponse
	 * @return
	 */
	public static JSONObject postForm(String url, Map<String, String> params, boolean needResponse){
		JSONObject jsonResult = null;
		LOG.info("http post begin, post url：" + url);
		try{
			CloseableHttpClient httpClient = HttpClients.createDefault();
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(180000).build();
			HttpPost postMethod = new HttpPost(url);
			
			postMethod.setConfig(requestConfig);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			
			Set<String> keySet = params.keySet();
			for(String key : keySet) {
				nvps.add(new BasicNameValuePair(key, params.get(key)));
			}
			LOG.info("post 请求参数：" + nvps.toString());
			LOG.info("post 请求url：" + url);
			postMethod.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			HttpResponse response = httpClient.execute(postMethod);
			url = URLDecoder.decode(url, "utf-8");
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				String str = "";
				str = EntityUtils.toString(response.getEntity());
				if(!needResponse){
					return null;
				}
				jsonResult = (JSONObject)JSONObject.parse(str);
				LOG.info("post 结果返回：" + jsonResult);
			} else {
				LOG.info("post failed：" + url+","+response.toString());
			}
		} catch (Exception e) {
			LOG.info("post failed：" + url+","+e.getMessage());
		}

		return jsonResult;
	}
	
	/**
	 * 返回http请求返回的字符串
	 * @param url
	 * @param params
	 * @return
	 */
	public static String postFormWithNativeResp(String url, Map<String, String> params){
		LOG.info("http post begin, post url：" + url);
		try{
			CloseableHttpClient httpClient = HttpClients.createDefault();
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(180000).build();
			
			HttpPost postMethod = new HttpPost(url);
			
			postMethod.setConfig(requestConfig);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			
			Set<String> keySet = params.keySet();
			for(String key : keySet) {
				nvps.add(new BasicNameValuePair(key, params.get(key)));
			}
			postMethod.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			HttpResponse response = httpClient.execute(postMethod);
			url = URLDecoder.decode(url, "utf-8");
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				String str = "";
				str = EntityUtils.toString(response.getEntity());
				return str;
			} else {
				LOG.info("post failed：" + url);
			}
		} catch (Exception e) {
			LOG.info("post failed：" + url);
		}
		LOG.info("post end success：" + url);
		return null;
	}
	
	public static JSONObject postForm(String url, Map<String, String> params){
		return postForm(url, params, true);
	}
	
	/**
	 * 通过httpclient批量上传文件
	 * @param url
	 * @param files
	 * @return
	 */
	public static JSONObject uploadMultipartFile(String url, Map<String, MultipartFile> files, Map<String, String> params){
		JSONObject jsonResult = null;
		try{
			CloseableHttpClient httpClient = HttpClients.createDefault();
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(180000).build();
			HttpPost postMethod = new HttpPost(url);
			postMethod.setConfig(requestConfig);
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			for(String key:files.keySet()){
				MultipartFile multipartFile = files.get(key);
				builder.addBinaryBody(key, multipartFile.getBytes(), ContentType.DEFAULT_BINARY, multipartFile.getName());
			}
			if(MapUtils.isNotEmpty(params)){
				for(String key:params.keySet()){
					builder.addTextBody(key, params.get(key), ContentType.TEXT_PLAIN.withCharset("UTF-8"));
				}
			}
			HttpEntity entity = builder.build();
			postMethod.setEntity(entity);
			HttpResponse response = httpClient.execute(postMethod);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				String str = "";
				str = EntityUtils.toString(response.getEntity());
				jsonResult = (JSONObject)JSONObject.parse(str);
				LOG.info("post 结果返回：" + jsonResult);
			} else {
				LOG.info("post failed：" + url+","+response.toString());
			}
		} catch (Exception e) {
			LOG.error("post 请求提交失败：" + url);
		}
		return jsonResult;
	}
	
}
