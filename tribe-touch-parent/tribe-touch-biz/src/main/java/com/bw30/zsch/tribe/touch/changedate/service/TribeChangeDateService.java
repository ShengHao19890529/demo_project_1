package com.bw30.zsch.tribe.touch.changedate.service;

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

/**
 * 改期接口服务
 * 
 * @author ShengHao
 *
 *         2017年7月27日 - 上午9:29:38
 */
public interface TribeChangeDateService {

	/**
	 * 查询可改期的旅客列表
	 */
	public TPQueryOrderTicketCanChangeDateResp queryChangeDatePassengerList(
			TPQueryOrderTicketCanChangeDateReq changeDatePassengerReq) throws Exception;

	/**
	 * 提交需要改期的乘客和航班，做校验使用，返回的数据也可以作为页面展示的数据
	 */
	public TPSubmitCanChangePassengersAndFlightResp submitCanChangePassengersFlight(
			TPSubmitCanChangePassengersAndFlightReq submitPassengerReq) throws Exception;

	/**
	 * 查询可改期的航班列表
	 */
	public TPQueryOrderFlightCanChangeDateResp queryChangeDateFlightList(
			TPQueryOrderFlightCanChangeDateReq changeDateFlightReq) throws Exception;

	/**
	 * 改期航班
	 */
	public TPChangeDateChargeResp changeDate(TPChangeDateChargeReq tpChangeDateChargeReq) throws Exception;

	/**
	 * 取消改期
	 */
	public TPCancelChangeDateResp cancelChangeDate(TPCancelChangeDateReq tpCancelChangeDateReq) throws Exception;

	/**
	 * 免费改期
	 */
	public TPSubmitProcessFreeDateChangeResp freeChangeDate(
			TPSubmitProcessFreeDateChangeReq submitProcessFreeDateChangeReq) throws Exception;

	/**
	 * 过滤航班列表
	 */
	public TPFilterAndSortReshopSearchResp filterFlightList(TPFilterAndSortReshopSearchReq filterFlightListReq)
			throws Exception;

}
