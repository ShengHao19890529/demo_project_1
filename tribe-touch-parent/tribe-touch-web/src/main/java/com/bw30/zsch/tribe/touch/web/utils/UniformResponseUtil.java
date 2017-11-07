package com.bw30.zsch.tribe.touch.web.utils;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSONObject;
import com.bw30.zsch.tribe.touch.constants.SystemResponseCodeEnum;

/**
 * 统一返回前台数据信息
 * 
 * @author ShengHao
 *
 *         2017年8月8日 - 上午9:52:51
 */
public class UniformResponseUtil {

	/**
	 * 给前台返回响应信息，参数自定义
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param responseCode
	 *            响应码<br>
	 *            com.bw.zsch.client.android.model.message.RespBody.
	 *            RespBodyStatus.code
	 * @param responseMsg
	 *            响应信息<br>
	 *            com.bw.zsch.client.android.model.message.RespBody.
	 *            RespBodyStatus.msg
	 */
	public static void responseAjaxData(HttpServletResponse response, int responseCode, String responseMsg) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		try {
			// 返回结果重新包装
			out = response.getWriter();
			out.write(buildResponseJSONObject(responseCode, responseMsg).toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * 给前台返回响应信息，参数枚举类
	 */
	public static void responseAjaxData(HttpServletResponse response, SystemResponseCodeEnum systemResponseCodeEnum) {
		responseAjaxData(response, systemResponseCodeEnum.getCode(), systemResponseCodeEnum.getMsg());
	}

	/**
	 * 组装响应对象，入参自定义
	 */
	public static JSONObject buildResponseJSONObject(int responseCode, String responseMsg) {
		JSONObject returnObj = new JSONObject();
		JSONObject bodyObj = new JSONObject();
		JSONObject messageObj = new JSONObject();
		messageObj.put("keyCode", responseCode);
		messageObj.put("value", responseMsg);
		bodyObj.put("message", messageObj);
		returnObj.put("body", bodyObj);
		return returnObj;
	}

	/**
	 * 组装响应对象，入参枚举
	 */
	public static JSONObject buildResponseJSONObject(SystemResponseCodeEnum systemResponseCodeEnum) {
		return buildResponseJSONObject(systemResponseCodeEnum.getCode(), systemResponseCodeEnum.getMsg());
	}

}
