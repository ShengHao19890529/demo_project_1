package com.bw30.zsch.tribe.touch.refund.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.bw.mall.client.service.client.dubbo.TheTribeClientService;
import com.bw.zsch.client.android.model.trip.refund.TPQueryOrderFlightAndPassengersCanRefundReq;
import com.bw.zsch.client.android.model.trip.refund.TPQueryOrderFlightAndPassengersCanRefundResp;
import com.bw.zsch.client.android.model.trip.refund.TPRefundOrderTicketReq;
import com.bw.zsch.client.android.model.trip.refund.TPRefundOrderTicketResp;
import com.bw.zsch.client.android.model.trip.refund.TPSubmitRefundPassengersAndFlightReq;
import com.bw.zsch.client.android.model.trip.refund.TPSubmitRefundPassengersAndFlightResp;
import com.bw30.zsch.tribe.touch.refund.service.TribeRefundService;

/**
 * 退票接口服务实现类
 * 
 * @author ShengHao
 * 
 *         2017年7月27日 - 上午9:28:58
 */
@Service
public class TribeRefundServiceImpl implements TribeRefundService {

	private final static Logger LOG = Logger.getLogger(TribeRefundServiceImpl.class);

	/**
	 * 四川航空port提供的dubbo服务
	 */
	@Autowired
	private TheTribeClientService theTribeClientService;

	@Override
	public TPQueryOrderFlightAndPassengersCanRefundResp queryRefundFlightPassengerList(
			TPQueryOrderFlightAndPassengersCanRefundReq tpRefundReq) throws Exception {

		LOG.info("查询可退的航班和乘客，入参：" + JSON.toJSONString(tpRefundReq));
		TPQueryOrderFlightAndPassengersCanRefundResp tpRefundResp = theTribeClientService
				.tp_query_order_flightandpassengers_can_refund(tpRefundReq);
		LOG.info("查询可退的航班和乘客，响应：" + JSON.toJSONString(tpRefundResp));

		return tpRefundResp;
	}

	@Override
	public TPSubmitRefundPassengersAndFlightResp submitRefund(TPSubmitRefundPassengersAndFlightReq submitRefundReq)
			throws Exception {

		LOG.info("发起退票操作，入参：" + JSON.toJSONString(submitRefundReq));
		TPSubmitRefundPassengersAndFlightResp tpRefundOperateResp = theTribeClientService
				.tp_submit_refund_passengersandflight(submitRefundReq);
		LOG.info("发起退票操作，响应：" + JSON.toJSONString(tpRefundOperateResp));

		return tpRefundOperateResp;
	}

	@Override
	public TPRefundOrderTicketResp refund(TPRefundOrderTicketReq tpRefundOrderTicketReq) throws Exception {

		LOG.info("退票，入参：" + JSON.toJSONString(tpRefundOrderTicketReq));
		TPRefundOrderTicketResp tpRefundResp = theTribeClientService.tp_refund_order_ticket(tpRefundOrderTicketReq);
		LOG.info("退票，响应：" + JSON.toJSONString(tpRefundResp));

		return tpRefundResp;
	}

}
