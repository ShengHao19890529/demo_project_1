package com.bw30.zsch.tribe.touch.dao.bean;

import java.io.Serializable;

public class MerchantOrderInfo implements Serializable {

	private static final long serialVersionUID = 6296337845269650988L;

	private Integer id;

	// 订单号
	private String merchantTradeSn;

	// 订单名称
	private String orderName;

	// 订单生成时间，格式为yyyyMMddHHmmss，如 2009 年 12 月 27日 9 点 10 分 10 秒表示为20091227091010
	private String createTime;

	// 收款方名称，如：海南航空股份有限公司
	private String receiveName;

	// 订单金额，分为单位
	private int amount;

	// 商户支付结果页面回调地址
	private String returnUrl;

	// 外部订单号，支付流水号
	private String outTradeSn;

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getMerchantTradeSn() {
		return merchantTradeSn;
	}

	public void setMerchantTradeSn(String merchantTradeSn) {
		this.merchantTradeSn = merchantTradeSn;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOutTradeSn() {
		return outTradeSn;
	}

	public void setOutTradeSn(String outTradeSn) {
		this.outTradeSn = outTradeSn;
	}

}