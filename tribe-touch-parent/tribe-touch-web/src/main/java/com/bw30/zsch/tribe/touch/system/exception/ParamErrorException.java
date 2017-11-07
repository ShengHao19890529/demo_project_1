package com.bw30.zsch.tribe.touch.system.exception;

/**
 * 参数非法异常类，用于 GlobalExceptionHandler 分类处理
 * 
 * @author ShengHao
 *
 *         2017年8月9日 - 上午9:17:37
 */
public class ParamErrorException extends Exception {

	private static final long serialVersionUID = 8076132634256272894L;

	public ParamErrorException() {
		super("参数有误");
	}

	public ParamErrorException(String errorMsg) {
		super(errorMsg);
	}

}
