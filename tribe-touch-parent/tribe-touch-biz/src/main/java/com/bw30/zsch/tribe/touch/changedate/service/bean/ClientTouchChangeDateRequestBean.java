package com.bw30.zsch.tribe.touch.changedate.service.bean;

import java.io.Serializable;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 安卓或者IOS客户端改期接入touch传递的参数对象
 * 
 * @author ShengHao
 *
 *         2017年8月9日 - 上午10:14:22
 */
public class ClientTouchChangeDateRequestBean implements Serializable {

	private static final long serialVersionUID = -8857158369842758673L;

	// 改期单号，这里可以使用国际化
	@NotEmpty(message = "订单号不可空")
	private String orderId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
