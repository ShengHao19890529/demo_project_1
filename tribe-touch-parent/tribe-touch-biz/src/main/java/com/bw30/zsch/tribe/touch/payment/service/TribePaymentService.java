package com.bw30.zsch.tribe.touch.payment.service;

import com.bw.zsch.client.android.model.trip.pay.TPGetPayChannelInfoReq;
import com.bw.zsch.client.android.model.trip.pay.TPGetPayChannelInfoResp;
import com.bw.zsch.client.android.model.trip.pay.TPPaymentReq;
import com.bw.zsch.client.android.model.trip.pay.TPPaymentResp;
import com.bw.zsch.client.android.model.trip.pay.TPSubmitPayReq;
import com.bw.zsch.client.android.model.trip.pay.TPSubmitPayResp;

/**
 * 支付接口服务
 * 
 * @author ShengHao
 *
 *         2017年7月27日 - 上午9:21:38
 */
public interface TribePaymentService {

	/**
	 * 提交支付，应该是做支付验证使用，不调用也能过，最好还是调用下
	 */
	public TPSubmitPayResp submitPay(TPSubmitPayReq tpSubmitPayReq) throws Exception;

	/**
	 * 查询支付列表
	 */
	public TPGetPayChannelInfoResp queryPayInfoList(TPGetPayChannelInfoReq tpGetPayChannelInfoReq) throws Exception;

	/**
	 * 订单支付
	 */
	public TPPaymentResp payment(TPPaymentReq tpPaymentReq) throws Exception;

}
