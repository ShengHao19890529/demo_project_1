package com.bw30.zsch.tribe.touch.system.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import com.bw.zsch.client.android.model.message.RespBody;
import com.bw30.zsch.tribe.touch.web.utils.UniformResponseUtil;

/**
 * 异常统一处理，封装错误消息返回给页面
 * 
 * @author ShengHao
 *
 *         2017年7月24日 - 上午9:14:03
 */
@Component
public class GlobalExceptionHandler implements HandlerExceptionResolver {

	private final static Logger LOG = Logger.getLogger(GlobalExceptionHandler.class);

	// 错误页面的根路径
	private final static String EXCEPTION_ROOT_DIR = "/exception";
	// 参数错误页面路径
	private final static String PARAM_ERROR_PAGE = EXCEPTION_ROOT_DIR + "/paramError.vm";

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		if (ex != null) {
			LOG.error("系统发生异常，异常信息：" + ex.toString());
			// 参数异常则跳转到参数异常提示页面
			if (ex instanceof ParamErrorException) {
				return new ModelAndView(PARAM_ERROR_PAGE);
			}
			// 其他异常，交由H5页面做浮层展示，提示错误一律网络异常
			else {
				UniformResponseUtil.responseAjaxData(response, RespBody.RespBodyStatus.NETWORK_BUSY.getCode(),
						RespBody.RespBodyStatus.NETWORK_BUSY.getMsg());
			}
		}
		return null;
	}

}
