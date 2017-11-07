package com.bw30.zsch.tribe.touch.payment.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.bw.mall.client.service.client.dubbo.TheTribeClientService;
import com.bw.zsch.client.android.model.trip.pay.TPGetPayChannelInfoReq;
import com.bw.zsch.client.android.model.trip.pay.TPGetPayChannelInfoResp;
import com.bw.zsch.client.android.model.trip.pay.TPPaymentReq;
import com.bw.zsch.client.android.model.trip.pay.TPPaymentResp;
import com.bw.zsch.client.android.model.trip.pay.TPSubmitPayReq;
import com.bw.zsch.client.android.model.trip.pay.TPSubmitPayResp;
import com.bw30.zsch.tribe.touch.payment.service.TribePaymentService;

/**
 * 支付接口服务实现类
 * 
 * @author ShengHao
 *
 *         2017年7月27日 - 上午9:21:52
 */
@Service
public class TribePaymentServiceImpl implements TribePaymentService {

	private final static Logger LOG = Logger.getLogger(TribePaymentServiceImpl.class);

	/**
	 * 四川航空port提供的dubbo服务
	 */
	@Autowired
	private TheTribeClientService theTribeClientService;

	@Override
	public TPSubmitPayResp submitPay(TPSubmitPayReq tpSubmitPayReq) throws Exception {

		LOG.info("提交支付验证，入参：" + JSON.toJSONString(tpSubmitPayReq));
		TPSubmitPayResp tpSubmitPayResp = theTribeClientService.tp_submit_pay(tpSubmitPayReq);
		LOG.info("提交支付验证，响应：" + JSON.toJSONString(tpSubmitPayResp));

		return tpSubmitPayResp;
	}

	@Override
	public TPGetPayChannelInfoResp queryPayInfoList(TPGetPayChannelInfoReq tpGetPayChannelInfoReq) throws Exception {

		LOG.info("查询支付列表，入参：" + JSON.toJSONString(tpGetPayChannelInfoReq));
		TPGetPayChannelInfoResp tpGetPayChannelInfoResp = null;// theTribeClientService.tp_get_pay_channel_info(tpGetPayChannelInfoReq);
		LOG.info("查询支付列表，响应：" + JSON.toJSONString(tpGetPayChannelInfoResp));

		return tpGetPayChannelInfoResp;
	}

	@Override
	public TPPaymentResp payment(TPPaymentReq tpPaymentReq) throws Exception {

		LOG.info("订单支付，入参：" + JSON.toJSONString(tpPaymentReq));
		TPPaymentResp tpPaymentResp = null;// theTribeClientService.tp_payment(tpPaymentReq);
		LOG.info("订单支付，响应：" + JSON.toJSONString(tpPaymentResp));

		return tpPaymentResp;
	}

}
