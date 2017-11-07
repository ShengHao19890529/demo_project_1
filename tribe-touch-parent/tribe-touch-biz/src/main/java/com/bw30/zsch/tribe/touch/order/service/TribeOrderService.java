package com.bw30.zsch.tribe.touch.order.service;

import com.bw.zsch.client.android.model.trip.order.TPQueryOrderDetailReq;
import com.bw.zsch.client.android.model.trip.order.TPQueryOrderDetailResp;
import com.bw.zsch.client.android.model.trip.order.TPQueryOrderReq;
import com.bw.zsch.client.android.model.trip.order.TPQueryOrderResp;

/**
 * 订单接口服务
 * 
 * @author ShengHao
 *
 *         2017年7月27日 - 上午9:20:34
 */
public interface TribeOrderService {

	/**
	 * 查询订单列表数据
	 */
	public TPQueryOrderResp queryOrderList(TPQueryOrderReq tpQueryOrderReq) throws Exception;

	/**
	 * 查询订单详情
	 */
	public TPQueryOrderDetailResp queryOrderDetail(TPQueryOrderDetailReq tpQueryOrderDetailReq) throws Exception;

}
