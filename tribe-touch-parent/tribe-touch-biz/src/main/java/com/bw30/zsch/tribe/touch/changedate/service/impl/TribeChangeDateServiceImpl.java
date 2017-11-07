package com.bw30.zsch.tribe.touch.changedate.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bw.mall.client.service.client.dubbo.TheTribeClientService;
import com.bw.zsch.client.android.model.trip.changedate.TPCancelChangeDateReq;
import com.bw.zsch.client.android.model.trip.changedate.TPCancelChangeDateResp;
import com.bw.zsch.client.android.model.trip.changedate.TPChangeDateChargeReq;
import com.bw.zsch.client.android.model.trip.changedate.TPChangeDateChargeResp;
import com.bw.zsch.client.android.model.trip.changedate.TPFilterAndSortReshopSearchReq;
import com.bw.zsch.client.android.model.trip.changedate.TPFilterAndSortReshopSearchResp;
import com.bw.zsch.client.android.model.trip.changedate.TPQueryOrderFlightCanChangeDateReq;
import com.bw.zsch.client.android.model.trip.changedate.TPQueryOrderFlightCanChangeDateResp;
import com.bw.zsch.client.android.model.trip.changedate.TPQueryOrderTicketCanChangeDateReq;
import com.bw.zsch.client.android.model.trip.changedate.TPQueryOrderTicketCanChangeDateResp;
import com.bw.zsch.client.android.model.trip.changedate.TPSubmitCanChangePassengersAndFlightReq;
import com.bw.zsch.client.android.model.trip.changedate.TPSubmitCanChangePassengersAndFlightResp;
import com.bw.zsch.client.android.model.trip.changedate.TPSubmitProcessFreeDateChangeReq;
import com.bw.zsch.client.android.model.trip.changedate.TPSubmitProcessFreeDateChangeResp;
import com.bw30.zsch.tribe.touch.changedate.service.TribeChangeDateService;

/**
 * 改期接口服务实现类
 * 
 * @author ShengHao
 *
 *         2017年7月27日 - 上午9:30:08
 */
@Service
public class TribeChangeDateServiceImpl implements TribeChangeDateService {

	private final static Logger LOG = Logger.getLogger(TribeChangeDateServiceImpl.class);

	/**
	 * 四川航空port提供的dubbo服务
	 */
	@Autowired
	private TheTribeClientService theTribeClientService;

	@Override
	public TPQueryOrderTicketCanChangeDateResp queryChangeDatePassengerList(
			TPQueryOrderTicketCanChangeDateReq changeDatePassengerReq) throws Exception {

		LOG.info("查询可改期的旅客列表，调用 tp_query_order_ticket_can_change_date ， 请求参数："
				+ JSON.toJSONString(changeDatePassengerReq));
		TPQueryOrderTicketCanChangeDateResp changeDatePassengerResponse = theTribeClientService
				.tp_query_order_ticket_can_change_date(changeDatePassengerReq);
		LOG.info("查询可改期的旅客列表，调用 tp_query_order_ticket_can_change_date ， 响应信息："
				+ JSON.toJSONString(changeDatePassengerResponse));

		return changeDatePassengerResponse;
	}

	@Override
	public TPSubmitCanChangePassengersAndFlightResp submitCanChangePassengersFlight(
			TPSubmitCanChangePassengersAndFlightReq submitPassengerReq) throws Exception {

		LOG.info("提交需要改期的乘客和航班，获取订单详情信息，调用 tp_submit_canchange_passengers_flight ， 请求参数："
				+ JSON.toJSONString(submitPassengerReq));
		TPSubmitCanChangePassengersAndFlightResp submitCanChangePassengersResponse = theTribeClientService
				.tp_submit_canchange_passengers_flight(submitPassengerReq);
		LOG.info("提交需要改期的乘客和航班，获取订单详情信息，调用 tp_submit_canchange_passengers_flight ， 响应信息："
				+ JSON.toJSONString(submitCanChangePassengersResponse));

		return submitCanChangePassengersResponse;
	}

	@Override
	public TPQueryOrderFlightCanChangeDateResp queryChangeDateFlightList(
			TPQueryOrderFlightCanChangeDateReq changeDateFlightReq) throws Exception {

		LOG.info(
				"查询可改期的航班列表，调用 tp_query_order_flight_can_change_date ， 请求参数：" + JSON.toJSONString(changeDateFlightReq));
		TPQueryOrderFlightCanChangeDateResp changeDateFlightListResponse = theTribeClientService
				.tp_query_order_flight_can_change_date(changeDateFlightReq);
		String jsonStr = JSON.toJSONString(changeDateFlightListResponse,
				SerializerFeature.DisableCircularReferenceDetect);
		TPQueryOrderFlightCanChangeDateResp resp = JSON.parseObject(jsonStr, TPQueryOrderFlightCanChangeDateResp.class);
		LOG.info("查询可改期的航班列表，调用 tp_query_order_flight_can_change_date ， 响应信息：" + JSON.toJSONString(resp));

		return resp;
	}

	@Override
	public TPChangeDateChargeResp changeDate(TPChangeDateChargeReq tpChangeDateChargeReq) throws Exception {

		LOG.info("改期航班，调用 tp_change_date ， 请求参数：" + JSON.toJSONString(tpChangeDateChargeReq));
		TPChangeDateChargeResp tpChangeDateChargeResp = theTribeClientService.tp_change_date(tpChangeDateChargeReq);
		LOG.info("改期航班，调用 tp_change_date ， 响应信息：" + JSON.toJSONString(tpChangeDateChargeResp));

		return tpChangeDateChargeResp;
	}

	@Override
	public TPCancelChangeDateResp cancelChangeDate(TPCancelChangeDateReq tpCancelChangeDateReq) throws Exception {

		LOG.info("取消改期，调用 tp_cancel_change_date ， 请求参数：" + JSON.toJSONString(tpCancelChangeDateReq));
		TPCancelChangeDateResp tpCancelChangeDateResp = theTribeClientService
				.tp_cancel_change_date(tpCancelChangeDateReq);
		LOG.info("取消改期，调用 tp_cancel_change_date ， 响应信息：" + JSON.toJSONString(tpCancelChangeDateResp));

		return tpCancelChangeDateResp;
	}

	@Override
	public TPSubmitProcessFreeDateChangeResp freeChangeDate(
			TPSubmitProcessFreeDateChangeReq submitProcessFreeDateChangeReq) throws Exception {

		LOG.info("免费改期，调用 tp_free_change_date ， 请求参数：" + JSON.toJSONString(submitProcessFreeDateChangeReq));
		TPSubmitProcessFreeDateChangeResp tpSubmitProcessFreeDateChangeResp = theTribeClientService
				.tp_free_change_date(submitProcessFreeDateChangeReq);
		LOG.info("免费改期，调用 tp_free_change_date ， 响应信息：" + JSON.toJSONString(tpSubmitProcessFreeDateChangeResp));

		return tpSubmitProcessFreeDateChangeResp;
	}

	@Override
	public TPFilterAndSortReshopSearchResp filterFlightList(TPFilterAndSortReshopSearchReq filterFlightListReq)
			throws Exception {

		LOG.info("过滤航班列表，调用 tp_filterandsort_reshop_search ， 请求参数：" + JSON.toJSONString(filterFlightListReq));
		TPFilterAndSortReshopSearchResp filterFlightListResp = theTribeClientService
				.tp_filterandsort_reshop_search(filterFlightListReq);

		String jsonStr = JSON.toJSONString(filterFlightListResp, SerializerFeature.DisableCircularReferenceDetect);
		// TPFilterAndSortFlightResp
		TPFilterAndSortReshopSearchResp resp = JSON.parseObject(jsonStr, TPFilterAndSortReshopSearchResp.class);

		LOG.info("过滤航班列表，调用 tp_filterandsort_reshop_search ， 响应信息：" + JSON.toJSONString(resp));

		return resp;
	}

}
