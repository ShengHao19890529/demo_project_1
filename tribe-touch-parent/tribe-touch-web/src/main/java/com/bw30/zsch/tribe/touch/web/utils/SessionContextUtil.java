package com.bw30.zsch.tribe.touch.web.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * session 上下文，可以全局获取session
 * 
 * @author ShengHao
 *
 *         2017年10月18日 - 下午2:15:27
 */
public class SessionContextUtil {

	public static HttpServletRequest getCurrentRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	public static HttpSession getCurrentSession() {
		return getCurrentRequest().getSession();
	}

	public static String getCurrentSessionId() {
		return getCurrentSession().getId();
	}

}
