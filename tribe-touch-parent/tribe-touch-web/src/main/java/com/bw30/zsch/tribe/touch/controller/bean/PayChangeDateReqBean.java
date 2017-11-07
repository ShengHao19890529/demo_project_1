package com.bw30.zsch.tribe.touch.controller.bean;

import java.io.Serializable;

/**
 * 付费改期请求入参bean
 * 
 * @author ShengHao
 *
 */
public class PayChangeDateReqBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3057010268844975124L;

	/**
	 * 改期订单号
	 */
	private String orderId;

	/**
	 * 改期支付金额
	 */
	private String payAmount;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

}
