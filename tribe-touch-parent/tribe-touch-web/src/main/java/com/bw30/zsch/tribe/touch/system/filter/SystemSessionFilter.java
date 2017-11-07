package com.bw30.zsch.tribe.touch.system.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import com.alibaba.fastjson.JSON;
import com.bw.zsch.client.android.model.message.RespBody.RespBodyStatus;
import com.bw30.zsch.tribe.touch.config.bean.TribePropertiesConfig;
import com.bw30.zsch.tribe.touch.constants.SystemConstants;
import com.bw30.zsch.tribe.touch.constants.SystemConstants.SymbolEnum;
import com.bw30.zsch.tribe.touch.service.util.SpringContextHolder;
import com.bw30.zsch.tribe.touch.web.utils.UniformResponseUtil;

/**
 * session过滤器，和登录校验相关的业务
 * 
 * @author ShengHao
 *
 *         2017年7月24日 - 上午10:29:53
 */
public class SystemSessionFilter implements Filter {

	private final static Logger LOG = Logger.getLogger(SystemSessionFilter.class);

	// 过滤器配置的不需要登录的初始化参数key
	private final static String INIT_PARAM_NOLOGINURL_KEY = "noLoginUrl";
	// 不需要登陆的地址列表
	private static List<String> NO_LOGIN_URL_LIST = new ArrayList<String>();

	// 初始化参数中静态资源的key
	private final static String INIT_PARAM_STATIC_RESOURCES = "staticResources";
	// 静态资源列表
	private static List<String> STATIC_RESOURCES_LIST = new ArrayList<String>();

	// ajax标志，H5页面传过来，非必填的
	public final static String IS_AJAX_REQUEST = "isAjax";
	// ajax请求中，header的value
	private final static String XML_HTTP_REQUEST = "XMLHttpRequest";
	// ajax请求中，header的key
	private final static String X_REQUESTED_WITH = "X-Requested-With";
	private final static String TRUE = "true";

	// 控制器统一请求前缀
	private final static String UNIFORM_CONTROLLER_PREFIX = "/tribe";
	// 登录页面请求路径
	private final static String GO_LOGIN_PAGE_URL = UNIFORM_CONTROLLER_PREFIX + "/user/goLoginPage";

	/**
	 * 项目的部署名称
	 */
	@Value("#{applicationConfig['tribe_touch_app_path']}")
	private String appPath;

	/**
	 * 初始化加载免登陆请求地址和静态资源后缀名列表
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		NO_LOGIN_URL_LIST = setInitParameters(filterConfig, INIT_PARAM_NOLOGINURL_KEY);
		LOG.info("【tribe-touch-web】应用当前配置免登陆请求地址列表：" + JSON.toJSONString(NO_LOGIN_URL_LIST));

		STATIC_RESOURCES_LIST = setInitParameters(filterConfig, INIT_PARAM_STATIC_RESOURCES);
		LOG.info("【tribe-touch-web】应用当前配置静态资源后缀列表：" + JSON.toJSONString(STATIC_RESOURCES_LIST));
	}

	/**
	 * 获取请求资源后缀名，如jquery-1.7.1.min.js返回js
	 */
	private String getRequestUrlSuffix(HttpServletRequest request) {
		String requestUrl = request.getRequestURL().toString();
		return requestUrl.substring(requestUrl.lastIndexOf(".") + 1, requestUrl.length());
	}

	/**
	 * 1、校验该请求是否需要登录 <br>
	 * 2、根据请求类型（ajax或浏览器地址）做响应的的跳转到登录页面
	 */
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		// 静态资源后缀配置为空 或者 当前请求的后缀不在静态资源配置的后缀列表中 则继续校验用户是否登录
		if (CollectionUtils.isEmpty(STATIC_RESOURCES_LIST)
				|| !STATIC_RESOURCES_LIST.contains(getRequestUrlSuffix(request))) {
			String lastRequestUrl = getRequestMappedUrl(request);

			LOG.info("当前用户请求路径：" + lastRequestUrl + " ， 登录的SessionId：" + request.getSession().getId() + " ， 登录的用户信息："
					+ JSON.toJSONString(request.getSession().getAttribute(SystemConstants.PORT_LOGIN_USER)));

			// 1、免登陆地址列表为空(说明所有请求都需要登录)或者请求地址不在免登陆地址列表中
			// 2、session中既没有port共享登录信息 也没有 自己的登录信息
			if ((CollectionUtils.isEmpty(NO_LOGIN_URL_LIST) || !NO_LOGIN_URL_LIST.contains(lastRequestUrl))
					&& request.getSession().getAttribute(SystemConstants.PORT_LOGIN_USER) == null
					&& request.getSession().getAttribute(SystemConstants.TOUCH_SESSION_USER_INFO) == null) {
				// 需要登录且未登陆，跳转到登录页面，这个暂时不用，全部用ajax做页面登录的跳转操作
				// goLoginPage(request, response);
				LOG.info("当前请求地址：" + lastRequestUrl + " ， 未在免登陆配置中 ， 请确认是否需要登陆 ， 免登陆在web.xml的noLoginUrl初始化参数中配置。");
				UniformResponseUtil.responseAjaxData(response, RespBodyStatus.MEMBER_NOT_LOG_IN_ERROR.getCode(),
						RespBodyStatus.MEMBER_NOT_LOG_IN_ERROR.getMsg());
				return;
			}
		}

		chain.doFilter(request, response);
	}

	/**
	 * 未登陆 跳转登录页面，分ajax请求和普通的地址请求
	 */
	@SuppressWarnings("unused")
	private void goLoginPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (isAjax(request)) {
			String requstUrl = request.getRequestURL().toString();
			String[] requstUrlArr = requstUrl.split("/tribe/");
			String loginUrl = requstUrlArr[0] + GO_LOGIN_PAGE_URL;
			PrintWriter printWriter = response.getWriter();
			printWriter.print("<script>window.location.href='" + loginUrl + "'</script>");
		} else {
			String redirectUrl = SpringContextHolder.getBean(TribePropertiesConfig.class).getAppPath()
					+ GO_LOGIN_PAGE_URL;
			response.sendRedirect(redirectUrl);
		}
	}

	@Override
	public void destroy() {
	}

	/**
	 * 获取请求地址最后/后面的字符串<br>
	 * 如<br>
	 * http://www.baidu.com/request/<br>
	 * 或<br>
	 * http://www.baidu.com/request<br>
	 * 则返回<br>
	 * /request
	 */
	private String getRequestMappedUrl(HttpServletRequest request) {
		String requestUrl = request.getRequestURL().toString();
		int requestUrlLastIndex = requestUrl.length() - 1;
		if (SymbolEnum.ENGLISH_RIGHT_XIEGANG.getSymbolValue().equals(requestUrl.charAt(requestUrlLastIndex) + "")) {
			requestUrl = requestUrl.substring(0, requestUrlLastIndex);
		}

		int currentLastRXGIndex = requestUrl.lastIndexOf(SymbolEnum.ENGLISH_RIGHT_XIEGANG.getSymbolValue());
		String lastRequestUrl = requestUrl.substring(currentLastRXGIndex, requestUrl.length());

		lastRequestUrl = substrBySessionId(lastRequestUrl, SystemConstants.TOMCAT_SESSION_NAME_CAPITAL);
		lastRequestUrl = substrBySessionId(lastRequestUrl, SystemConstants.TOMCAT_SESSION_NAME_LOWERCASE);

		return lastRequestUrl;
	}

	/**
	 * 根据session的name做截取
	 * 
	 * @param lastRequestUrl
	 *            经过处理的请求后缀
	 * @param sessionIdName
	 *            session的name
	 * @return /getPicVerifyCode;jsessionid=CB8B0E07DA6FC7B1CA681F5DE809F406-n2
	 *         则返回/getPicVerifyCode
	 */
	private String substrBySessionId(String lastRequestUrl, String sessionName) {
		if (lastRequestUrl.contains(sessionName)) {
			String splitStr = ";" + sessionName;
			lastRequestUrl = lastRequestUrl.split(splitStr)[0];
		}
		return lastRequestUrl;
	}

	/**
	 * 将过滤器的初始化参数装配到集合中
	 */
	private List<String> setInitParameters(FilterConfig filterConfig, String key) {
		List<String> arrayList = new ArrayList<String>();
		String initParameterValue = filterConfig.getInitParameter(key);
		if (StringUtils.isEmpty(initParameterValue)) {
			return null;
		}
		String[] initParameterValueArr = initParameterValue.split(SymbolEnum.ENGLISH_DOUHAO.getSymbolValue());
		if (initParameterValueArr != null && initParameterValueArr.length > 0) {
			for (String initParameterValueStr : initParameterValueArr) {
				String str = StringUtils.trim(initParameterValueStr);
				if (StringUtils.isNotEmpty(str))
					arrayList.add(str);
			}
		}
		return arrayList;
	}

	/**
	 * 判断当前请求是否为ajax请求
	 */
	private boolean isAjax(HttpServletRequest request) {
		return XML_HTTP_REQUEST.equalsIgnoreCase(request.getHeader(X_REQUESTED_WITH))
				|| TRUE.equals(request.getParameter(IS_AJAX_REQUEST));
	}

}
