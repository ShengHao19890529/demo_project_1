package com.bw30.zsch.tribe.touch.controller;

import org.springframework.web.servlet.ModelAndView;
import com.bw.zsch.client.android.model.message.RespBody;

/**
 * Tribe控制器基类，用于定义一些公共的信息
 * 
 * @author ShengHao
 *
 *         2017年7月25日 - 上午10:02:37
 */
public class TribeBaseController {

	/**
	 * 组装视图对象
	 * 
	 * @param path
	 *            视图所在工程路径
	 */
	protected ModelAndView buidlModelAndView(String path) {
		ModelAndView mav = new ModelAndView(path);
		return mav;
	}

	/**
	 * 解析调用port dubbo接口的响应结果<br>
	 * 
	 * @return true：响应成功，false：响应失败
	 */
	protected boolean parseInvokePortDubboResult(RespBody respBody) {
		if (respBody == null) {
			return false;
		}
		if (respBody.getMessage() == null && respBody.getStatus() == null) {
			return false;
		}
		// 这个0是川航port下com.bw.mall.client.config.MyConfig.ErrorCodeEnum的值
		if (respBody.getMessage() != null && respBody.getMessage().getKeyCode() % 10 == 0) {
			return true;
		}
		if (respBody.getStatus() != null && respBody.getStatus().getCode() % 10 == 0) {
			return true;
		}
		return false;
	}

}
