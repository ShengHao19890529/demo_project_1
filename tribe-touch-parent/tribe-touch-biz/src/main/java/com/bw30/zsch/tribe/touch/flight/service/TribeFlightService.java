package com.bw30.zsch.tribe.touch.flight.service;

import com.bw.zsch.client.android.model.trip.flight.TPAddShoppingCartReq;
import com.bw.zsch.client.android.model.trip.flight.TPAddShoppingCartResp;
import com.bw.zsch.client.android.model.trip.flight.TPCheckoutShoppingCartToPassengerDetailsReq;
import com.bw.zsch.client.android.model.trip.flight.TPCheckoutShoppingCartToPassengerDetailsResp;
import com.bw.zsch.client.android.model.trip.flight.TPFilterAndSortFlightReq;
import com.bw.zsch.client.android.model.trip.flight.TPFilterAndSortFlightResp;
import com.bw.zsch.client.android.model.trip.flight.TPQueryFlightReq;
import com.bw.zsch.client.android.model.trip.flight.TPQueryFlightResp;
import com.bw.zsch.client.android.model.trip.flight.TPSubmitPassengerDetailsReq;
import com.bw.zsch.client.android.model.trip.flight.TPSubmitPassengerDetailsResp;

/**
 * 航班服务接口
 * 
 * @author ShengHao
 *
 *         2017年7月24日 - 上午9:41:40
 */
public interface TribeFlightService {

	/**
	 * 查询航班列表
	 */
	public TPQueryFlightResp queryFlightList(TPQueryFlightReq tpQueryFlightReq) throws Exception;

	/**
	 * 过滤和排序航班列表
	 */
	public TPFilterAndSortFlightResp filterAndSortFlight(TPFilterAndSortFlightReq filterAndSortFlightReq)
			throws Exception;

	/**
	 * 点击"订票"按钮，将航班舱位信息添加到购物车里
	 */
	public TPAddShoppingCartResp addShoppingcart(TPAddShoppingCartReq tpAddShoppingCartReq) throws Exception;

	/**
	 * 提供信息确认页面展示的数据
	 */
	public TPCheckoutShoppingCartToPassengerDetailsResp checkoutShoppingcart(
			TPCheckoutShoppingCartToPassengerDetailsReq retriveShoppingCartReq) throws Exception;

	/**
	 * 创建订单，提交乘机人信息
	 */
	public TPSubmitPassengerDetailsResp submitOrder(TPSubmitPassengerDetailsReq submitOrderReq) throws Exception;

}
