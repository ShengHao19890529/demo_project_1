package com.bw30.zsch.tribe.touch.controller.bean;

import java.io.Serializable;
import com.alibaba.fastjson.JSONObject;

/**
 * tribe touch的统一返回对象
 * 
 * @author ShengHao
 *
 *         2017年10月11日 - 下午2:52:53
 */
public class TribeTouchResponseBean<T> implements Serializable {

	private static final long serialVersionUID = -7763222384599684505L;

	/**
	 * port返回的对象
	 */
	private T responseObj;

	/**
	 * touch返回的拓展数据
	 */
	private JSONObject extJsonObj;

	public T getResponseObj() {
		return responseObj;
	}

	public void setResponseObj(T responseObj) {
		this.responseObj = responseObj;
	}

	public JSONObject getExtJsonObj() {
		return extJsonObj;
	}

	public void setExtJsonObj(JSONObject extJsonObj) {
		this.extJsonObj = extJsonObj;
	}

}
