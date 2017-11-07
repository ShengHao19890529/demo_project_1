package com.bw30.zsch.tribe.touch.refund.service;

import com.bw.zsch.client.android.model.trip.refund.TPQueryOrderFlightAndPassengersCanRefundReq;
import com.bw.zsch.client.android.model.trip.refund.TPQueryOrderFlightAndPassengersCanRefundResp;
import com.bw.zsch.client.android.model.trip.refund.TPRefundOrderTicketReq;
import com.bw.zsch.client.android.model.trip.refund.TPRefundOrderTicketResp;
import com.bw.zsch.client.android.model.trip.refund.TPSubmitRefundPassengersAndFlightReq;
import com.bw.zsch.client.android.model.trip.refund.TPSubmitRefundPassengersAndFlightResp;

/**
 * 退票接口服务
 * 
 * @author ShengHao
 *
 *         2017年7月27日 - 上午9:28:37
 */
public interface TribeRefundService {

	/**
	 * 第一步：查询可退的航班和乘机人
	 */
	public TPQueryOrderFlightAndPassengersCanRefundResp queryRefundFlightPassengerList(
			TPQueryOrderFlightAndPassengersCanRefundReq tpRefundReq) throws Exception;

	/**
	 * 第二步：发起退票操作
	 */
	public TPSubmitRefundPassengersAndFlightResp submitRefund(TPSubmitRefundPassengersAndFlightReq submitRefundReq)
			throws Exception;

	/**
	 * 第三步：退票
	 */
	public TPRefundOrderTicketResp refund(TPRefundOrderTicketReq tpRefundOrderTicketReq) throws Exception;

}
