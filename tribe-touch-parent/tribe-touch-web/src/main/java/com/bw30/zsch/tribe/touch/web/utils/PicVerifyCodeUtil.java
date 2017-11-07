package com.bw30.zsch.tribe.touch.web.utils;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.alibaba.fastjson.JSONObject;
import com.bw30.zsch.tribe.touch.constants.SystemConstants;
import com.bw30.zsch.tribe.touch.constants.SystemResponseCodeEnum;

/**
 * 图片验证码一致性校验工具
 * 
 * @author ShengHao
 *
 *         2017年9月14日 - 上午10:26:59
 */
public class PicVerifyCodeUtil {

	private final static Logger LOG = Logger.getLogger(PicVerifyCodeUtil.class);

	/**
	 * 验证码参数在request中(get请求或者post请求后面加验证码参数)，返回客户端响应数据
	 */
	public static JSONObject buildVerifyResponseInGet(HttpServletRequest request) {
		return buildVerifyResponseInPost(request.getParameter(SystemConstants.PIC_VERIFY_CODE_SESSION_KEY), request);
	}

	/**
	 * 验证码参数在post body中，返回客户端响应数据
	 */
	public static JSONObject buildVerifyResponseInPost(String requestPicVerifyCode, HttpServletRequest request) {
		JSONObject returnObj;
		if (isVerifySuccessInPost(requestPicVerifyCode, request)) {
			returnObj = UniformResponseUtil.buildResponseJSONObject(SystemResponseCodeEnum.OK);
		} else {
			returnObj = UniformResponseUtil.buildResponseJSONObject(SystemResponseCodeEnum.PIC_VERIFY_CODE_ERROR);
		}
		return returnObj;
	}

	/**
	 * 验证码参数在request中(get请求或者post请求后面加验证码参数)
	 * 
	 * @return true：图片验证码一致，false：图片验证不一致
	 */
	public static boolean isVerifySuccessInGet(HttpServletRequest request) {
		return isVerifySuccessInPost(request.getParameter(SystemConstants.PIC_VERIFY_CODE_SESSION_KEY), request);
	}

	/**
	 * post请求验证校验码一致性
	 * 
	 * @return true：图片验证码一致，false：图片验证不一致
	 */
	public static boolean isVerifySuccessInPost(String requestPicVerifyCode, HttpServletRequest request) {

		if (StringUtils.isEmpty(requestPicVerifyCode)) {
			LOG.info("请求的参数中没有验证码字段");
			return false;
		}

		String sessionPicVerifyCode = (String) request.getSession()
				.getAttribute(SystemConstants.PIC_VERIFY_CODE_SESSION_KEY);

		if (StringUtils.isEmpty(sessionPicVerifyCode)) {
			LOG.info("session中没有验证码");
			return false;
		}

		if (!requestPicVerifyCode.equals(sessionPicVerifyCode)) {
			LOG.info("请求参数中的验证码：" + requestPicVerifyCode + " ， session中没有验证码：" + sessionPicVerifyCode + " ， 不一致！");
			return false;
		}

		return true;

	}

}
