package com.bw30.zsch.tribe.touch.flight.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.bw.mall.client.service.client.dubbo.TheTribeClientService;
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
import com.bw30.zsch.tribe.touch.flight.service.TribeFlightService;

/**
 * 航班服务接口实现类
 * 
 * @author ShengHao
 *
 *         2017年7月24日 - 上午9:44:15
 */
@Service
public class TribeFlightServiceImpl implements TribeFlightService {

	private final static Logger LOG = Logger.getLogger(TribeFlightServiceImpl.class);

	/**
	 * 四川航空port提供的dubbo服务
	 */
	@Autowired
	private TheTribeClientService theTribeClientService;

	@Override
	public TPQueryFlightResp queryFlightList(TPQueryFlightReq tpQueryFlightReq) throws Exception {

		LOG.info("查询航班列表接口 tp_query_flight 入参：" + JSON.toJSONString(tpQueryFlightReq));
		TPQueryFlightResp tpQueryFlightResp = theTribeClientService.tp_query_flight(tpQueryFlightReq);
		LOG.info("查询航班列表接口 tp_query_flight 返回：" + JSON.toJSONString(tpQueryFlightResp));

		return tpQueryFlightResp;
	}

	@Override
	public TPFilterAndSortFlightResp filterAndSortFlight(TPFilterAndSortFlightReq filterAndSortFlightReq)
			throws Exception {

		LOG.info("过滤和排序航班列表接口 tp_query_flight 入参：" + JSON.toJSONString(filterAndSortFlightReq));
		TPFilterAndSortFlightResp tpFilterAndSortFlightResp = theTribeClientService
				.tp_filterandsort_flight(filterAndSortFlightReq);
		LOG.info("过滤和排序航班列表接口 tp_query_flight 返回：" + JSON.toJSONString(tpFilterAndSortFlightResp));

		return tpFilterAndSortFlightResp;
	}

	@Override
	public TPAddShoppingCartResp addShoppingcart(TPAddShoppingCartReq tpAddShoppingCartReq) throws Exception {

		LOG.info("调用添加航班舱位信息到购物车接口 tp_add_shoppingcart 入参：" + JSON.toJSONString(tpAddShoppingCartReq));
		TPAddShoppingCartResp tpAddShoppingCartResp = theTribeClientService.tp_add_shoppingcart(tpAddShoppingCartReq);
		LOG.info("调用添加航班舱位信息到购物车接口 tp_add_shoppingcart 返回：" + JSON.toJSONString(tpAddShoppingCartResp));

		return tpAddShoppingCartResp;
	}

	@Override
	public TPCheckoutShoppingCartToPassengerDetailsResp checkoutShoppingcart(
			TPCheckoutShoppingCartToPassengerDetailsReq retriveShoppingCartReq) throws Exception {

		LOG.info("调用获取信息确认页面数据接口 tp_checkout_shoppingcart 入参：" + JSON.toJSONString(retriveShoppingCartReq));
		TPCheckoutShoppingCartToPassengerDetailsResp retriveShoppingCartResponse = theTribeClientService
				.tp_checkout_shoppingcart(retriveShoppingCartReq);
		LOG.info("调用获取信息确认页面数据接口 tp_checkout_shoppingcart 返回：" + JSON.toJSONString(retriveShoppingCartResponse));

		return retriveShoppingCartResponse;
	}

	@Override
	public TPSubmitPassengerDetailsResp submitOrder(TPSubmitPassengerDetailsReq submitOrderReq) throws Exception {

		LOG.info("提交订单-乘机人信息接口 tp_submit_passenger_details 入参：" + JSON.toJSONString(submitOrderReq));
		TPSubmitPassengerDetailsResp submitOrderResponse = theTribeClientService
				.tp_submit_passenger_details(submitOrderReq);
		LOG.info("提交订单-乘机人信息接口 tp_submit_passenger_details 返回：" + JSON.toJSONString(submitOrderResponse));

		return submitOrderResponse;
	}

}
