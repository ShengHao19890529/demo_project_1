package com.bw30.zsch.tribe.touch.constants;

import com.bw.zsch.client.android.model.message.RespBody.RespBodyStatus;

/**
 * 系统返回的错误信息提示
 * 
 * @author ShengHao
 *
 *         2017年7月26日 - 下午1:32:56
 */
public enum SystemResponseCodeEnum {

	OK(RespBodyStatus.MEMBER_NOT_LOG_IN_ERROR.getCode(), RespBodyStatus.MEMBER_NOT_LOG_IN_ERROR.getMsg()),

	NETWORK_BUSY(2, "网络繁忙，请稍后重试"),

	PIC_VERIFY_CODE_ERROR(3, "验证码不一致，请求重新输入"),

	CLIENT_CHANGE_DATE_REQUEST_PARAM_ERROR(90001, "客户端请求改期参数不合法");

	private int code;

	private String msg;

	private SystemResponseCodeEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
