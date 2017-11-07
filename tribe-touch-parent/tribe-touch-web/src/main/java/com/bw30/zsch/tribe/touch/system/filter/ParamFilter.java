package com.bw30.zsch.tribe.touch.system.filter;

import static com.bwhk.mall.client.service.client.dubbo.utils.TribeInterfaceContextConstants.*;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.MDC;
import com.bw30.zsch.tribe.touch.utils.StringUtil;
import com.bw30.zsch.tribe.touch.web.utils.ThreadLocalUtils;

/**
 * 系统添加公共参数过滤器<br>
 * v1：添加调用dubbo所需设置的sessionId和sequenceId<br>
 * v2：添加log4j打印时候的流水号sequenceId
 * 
 * @author ShengHao
 *
 *         2017年7月24日 - 上午8:59:44
 */
public class ParamFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		// 设置当前请求的会话ID
		ThreadLocalUtils.setSessionId(request);
		// 模拟Junit单元测试会话
		// ThreadLocalUtils.setSessionId("60b9999fd9f745b5b11d1c79d8e48969");
		// 设置当前请求的流水号
		ThreadLocalUtils.setSequenceId(StringUtil.getUUID());
		// LOG4J使用，统一添加当前的流水号
		MDC.put(SEQUENCE_ID, ThreadLocalUtils.getSequenceId());
		MDC.put(SESSION_ID, ThreadLocalUtils.getSessionId());
		chain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {

	}

}
