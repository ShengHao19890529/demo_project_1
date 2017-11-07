package com.bw30.zsch.tribe.touch.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * Http client工具
 * 
 * @author ShengHao
 *
 *         2016年12月7日-下午6:23:54
 */
public class HttpUtil {

	private static final Logger LOG = Logger.getLogger(HttpUtil.class);

	private final static int TIME_OUT = 6000;

	private static RequestConfig buildRequestConfig() {
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(TIME_OUT).build();
		return requestConfig;
	}

	public static String httpPost(String url, Map<String, String> paramsMap, String encoding) throws Exception {

		String resultStr = "";

		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();

		// 创建httppost
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(buildRequestConfig());
		// 创建参数列表
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();

		if (paramsMap != null && !paramsMap.isEmpty()) {
			for (Entry<String, String> entry : paramsMap.entrySet()) {
				paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}

		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(paramList, encoding);
			httpPost.setEntity(uefEntity);
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				resultStr = EntityUtils.toString(entity, encoding);
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultStr;
	}

	public static String httpGet(String url, Map<String, String> paramsMap) {
		String resultStr = "";
		StringBuffer buffer = new StringBuffer();
		if (paramsMap != null && paramsMap.size() > 0) {
			if (url.indexOf("?") < 0) {
				buffer.append("?");
			}
			Set<String> keySet = paramsMap.keySet();
			for (String key : keySet) {
				buffer.append(key).append("=").append(paramsMap.get(key) == null ? "" : paramsMap.get(key)).append("&");
			}
			buffer.deleteCharAt(buffer.length() - 1);
		}

		String getUrl = url + buffer.toString();

		LOG.info("GET URL：" + getUrl);

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(getUrl);
		httpGet.setConfig(buildRequestConfig());
		BufferedReader reader = null;
		try {
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
			reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = reader.readLine()) != null) {
				response.append(inputLine);
			}

			resultStr = response.toString();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return resultStr;
	}

}