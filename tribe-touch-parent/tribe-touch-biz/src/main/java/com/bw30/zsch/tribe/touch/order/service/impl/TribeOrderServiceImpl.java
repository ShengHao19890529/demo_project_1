package com.bw30.zsch.tribe.touch.order.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.bw.mall.client.service.client.dubbo.TheTribeClientService;
import com.bw.zsch.client.android.model.trip.order.TPQueryOrderDetailReq;
import com.bw.zsch.client.android.model.trip.order.TPQueryOrderDetailResp;
import com.bw.zsch.client.android.model.trip.order.TPQueryOrderReq;
import com.bw.zsch.client.android.model.trip.order.TPQueryOrderResp;
import com.bw30.zsch.tribe.touch.order.service.TribeOrderService;

/**
 * 订单接口服务实现类
 * 
 * @author ShengHao
 *
 *         2017年7月27日 - 上午9:21:03
 */
@Service
public class TribeOrderServiceImpl implements TribeOrderService {

	private final static Logger LOG = Logger.getLogger(TribeOrderServiceImpl.class);

	/**
	 * 四川航空port提供的dubbo服务
	 */
	@Autowired
	private TheTribeClientService theTribeClientService;

	@Override
	public TPQueryOrderResp queryOrderList(TPQueryOrderReq tpQueryOrderReq) throws Exception {

		LOG.info("查询订单列表，调用 tp_query_order ， 请求参数：" + JSON.toJSONString(tpQueryOrderReq));
		TPQueryOrderResp tpQueryOrderResp = theTribeClientService.tp_query_order(tpQueryOrderReq);
		LOG.info("查询订单列表，调用 tp_query_order ， 响应信息：" + JSON.toJSONString(tpQueryOrderResp));

		return tpQueryOrderResp;
	}

	@Override
	public TPQueryOrderDetailResp queryOrderDetail(TPQueryOrderDetailReq tpQueryOrderDetailReq) throws Exception {

		LOG.info("查询订单详情，调用 tp_query_order_detail ， 请求参数：" + JSON.toJSONString(tpQueryOrderDetailReq));
		TPQueryOrderDetailResp tpQueryOrderDetailResp = theTribeClientService
				.tp_query_order_detail(tpQueryOrderDetailReq);
		LOG.info("查询订单详情，调用 tp_query_order_detail ， 响应信息：" + JSON.toJSONString(tpQueryOrderDetailResp));

		return tpQueryOrderDetailResp;
	}

}
