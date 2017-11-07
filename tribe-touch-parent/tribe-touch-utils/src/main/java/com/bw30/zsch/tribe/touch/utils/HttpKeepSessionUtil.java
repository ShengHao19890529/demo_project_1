package com.bw30.zsch.tribe.touch.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.log4j.Logger;
import com.alibaba.fastjson.JSONObject;

/**
 * HttpClient工具-保持和服务端的session会话
 * 
 * @author ShengHao
 *
 *         2017年7月28日 - 上午11:24:31
 */
public class HttpKeepSessionUtil {

	private final static Logger LOG = Logger.getLogger(HttpKeepSessionUtil.class);

	// tomcat配置的sessionid名称，默认JSESSIONID
	public final static String TOMCAT_SESSION_NAME = "JSESSIONID";
	private final static String CHAR_SET_CODE = "UTF-8";

	private volatile static CloseableHttpClient HTTP_CLIENT = null;
	private volatile static RequestConfig REQUEST_CONFIG = null;

	// 锁对象
	private final static Object LOCK_OBJ = new Object();

	static {
		if (HTTP_CLIENT == null) {
			synchronized (LOCK_OBJ) {
				if (HTTP_CLIENT == null) {
					init();
				}
			}
		}
	}

	private static void init() {
		// 配置超时时间（连接服务端超时1秒，请求数据返回超时2秒）
		REQUEST_CONFIG = RequestConfig.custom().setConnectTimeout(120000).setSocketTimeout(60000)
				.setConnectionRequestTimeout(60000).build();
		// 设置默认跳转以及存储cookie
		HTTP_CLIENT = HttpClientBuilder.create().setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
				.setRedirectStrategy(new DefaultRedirectStrategy()).setDefaultRequestConfig(REQUEST_CONFIG)
				.setDefaultCookieStore(new BasicCookieStore()).build();
	}

	/**
	 * 将参数的map转成k1=v1&k2=v2...
	 */
	public static String mapToUrlParam(Map<String, String> parameterMap) {
		StringBuffer buf = new StringBuffer();
		if (parameterMap != null && !parameterMap.isEmpty()) {
			for (Entry<String, String> entry : parameterMap.entrySet()) {
				buf.append(entry.getKey());
				buf.append("=");
				buf.append(entry.getValue());
				buf.append("&");
			}
		}
		String bufStr = buf.toString();
		if (StringUtils.isNotEmpty(bufStr)) {
			bufStr = bufStr.substring(0, bufStr.length() - 1);
		}
		return bufStr;
	}

	/**
	 * 从响应中解析sessionId
	 */
	private static String parseSessionId(HttpClientContext httpClientContext) {
		if (httpClientContext != null) {
			CookieStore responseCookieStore = httpClientContext.getCookieStore();
			List<Cookie> cookies = responseCookieStore.getCookies();
			for (Cookie cookie : cookies) {
				if (TOMCAT_SESSION_NAME.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	public static String get(String getUrl, Map<String, String> parameterMap, String jSessionId) throws Exception {

		if (parameterMap != null && !parameterMap.isEmpty()) {
			String lastStr = getUrl.substring(getUrl.length() - 1, getUrl.length());
			if (!"?".equals(lastStr)) {
				getUrl += "?";
			}
			getUrl += mapToUrlParam(parameterMap);
		}

		HttpGet httpGet = new HttpGet(getUrl);
		if (StringUtils.isNotEmpty(jSessionId)) {
			httpGet.addHeader(TOMCAT_SESSION_NAME, jSessionId);
		}

		String responseStr = null;
		HttpClientContext httpClientContext = HttpClientContext.create();
		CloseableHttpResponse response = null;
		try {
			response = HTTP_CLIENT.execute(httpGet, httpClientContext);
			responseStr = parseResponse(response);
			LOG.info("http get response：" + responseStr);
			String responseSessioId = parseSessionId(httpClientContext);
			LOG.info("http get response sessioId：" + responseSessioId);
		} finally {
			closeCloseableHttpResponse(response);
		}
		return responseStr;
	}

	private static String parseResponse(CloseableHttpResponse response) throws Exception {
		BufferedReader reader = null;
		StringBuilder buf = new StringBuilder();
		String str = null;
		try {
			reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), CHAR_SET_CODE));
			while ((str = reader.readLine()) != null) {
				buf.append(str);
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return buf.toString();
	}

	private static void closeCloseableHttpResponse(CloseableHttpResponse response) throws IOException {
		if (response != null) {
			response.close();
		}
	}

	public static String post(String postUrl, Map<String, Object> parameterMap, String jsessioId) throws Exception {
		HttpPost httpPost = new HttpPost(postUrl);
		httpPost.addHeader("Content-Type", "application/json; charset=utf-8");
		httpPost.addHeader("Accept", "appliFcation/json");
		httpPost.setEntity(
				new StringEntity(new JSONObject(parameterMap).toJSONString(), Charset.forName(CHAR_SET_CODE)));

		if (StringUtils.isNotEmpty(jsessioId)) {
			httpPost.addHeader(TOMCAT_SESSION_NAME, jsessioId);
		}
		HttpClientContext httpClientContext = HttpClientContext.create();
		CloseableHttpResponse response = null;
		String responseStr = null;
		try {
			response = HTTP_CLIENT.execute(httpPost, httpClientContext);
			responseStr = parseResponse(response);
			LOG.info("http post response：" + responseStr);
			String responseSessioId = parseSessionId(httpClientContext);
			LOG.info("http post response sessioId：" + responseSessioId);
		} finally {
			closeCloseableHttpResponse(response);
		}

		return responseStr;
	}

	/**
	 * 手动增加cookie，暂时用不到<br>
	 */
	public static void addCookie(String name, String value, String domain, String path,
			HttpClientContext httpClientContext) {
		CookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie cookie = new BasicClientCookie(name, value);
		cookie.setDomain(domain);
		cookie.setPath(path);
		cookieStore.addCookie(cookie);
		httpClientContext.setCookieStore(cookieStore);
	}

	public static void main(String[] args) throws Exception {

		String getUrl = "http://172.25.104.229:8080/tribe-touch-web/tribe/login/login";

		Map<String, Object> jsonObject = new HashMap<String, Object>();
		jsonObject.put("username", "盛好");
		jsonObject.put("password", "123456");

		String id = post(getUrl, jsonObject, null);
		LOG.info(id);
		id = post(getUrl, jsonObject, null);
		LOG.info(id);
		id = post(getUrl, jsonObject, null);
		LOG.info(id);

	}

}
