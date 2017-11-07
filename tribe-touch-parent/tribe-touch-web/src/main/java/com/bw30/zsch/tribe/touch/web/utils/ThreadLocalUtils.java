package com.bw30.zsch.tribe.touch.web.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 线程本地变量工具
 * 
 * @author ShengHao
 *
 *         2017年7月24日 - 上午9:14:18
 */
public class ThreadLocalUtils {

	/**
	 * 存储tomcat会话ID的线程本地变量
	 */
	private final static ThreadLocal<String> SESSION_ID_THREAD_LOCAL = new ThreadLocal<String>();

	/**
	 * 存储流水号的线程本地变量
	 */
	private final static ThreadLocal<String> SEQUENCE_ID_THREAD_LOCAL = new ThreadLocal<String>();

	/**
	 * 向当前线程变量添加会话ID
	 * 
	 * @param sessionId
	 *            当前请求的会话ID
	 */
	public static void setSessionId(String sessionId) {
		SESSION_ID_THREAD_LOCAL.set(sessionId);
	}

	/**
	 * 向当前线程变量添加会话ID
	 * 
	 * @param request
	 *            当前请求的request
	 */
	public static void setSessionId(HttpServletRequest request) {
		setSessionId(request.getSession().getId());
	}

	/**
	 * 获取当前请求线程的sessionId
	 */
	public static String getSessionId() {
		return SESSION_ID_THREAD_LOCAL.get();
	}

	/**
	 * 本地线程变量设置流水号
	 * 
	 * @param sequenceId
	 *            流水号
	 */
	public static void setSequenceId(String sequenceId) {
		SEQUENCE_ID_THREAD_LOCAL.set(sequenceId);
	}

	/**
	 * 获取当前请求线程的流水号
	 */
	public static String getSequenceId() {
		return SEQUENCE_ID_THREAD_LOCAL.get();
	}

}
