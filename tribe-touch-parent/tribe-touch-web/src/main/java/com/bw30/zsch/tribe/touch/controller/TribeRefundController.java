package com.bw30.zsch.tribe.touch.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.bw.zsch.client.android.model.trip.refund.TPQueryOrderFlightAndPassengersCanRefundReq;
import com.bw.zsch.client.android.model.trip.refund.TPQueryOrderFlightAndPassengersCanRefundResp;
import com.bw.zsch.client.android.model.trip.refund.TPRefundOrderTicketReq;
import com.bw.zsch.client.android.model.trip.refund.TPRefundOrderTicketResp;
import com.bw.zsch.client.android.model.trip.refund.TPSubmitRefundPassengersAndFlightReq;
import com.bw.zsch.client.android.model.trip.refund.TPSubmitRefundPassengersAndFlightResp;
import com.bw30.zsch.tribe.touch.refund.service.TribeRefundService;

/**
 * 退票控制器
 * 
 * @author ShengHao
 *
 *         2017年7月27日 - 上午9:26:19
 */
@Controller
@RequestMapping(value = "/tribe/refund")
public class TribeRefundController extends TribeBaseController {

	// 退款页面的根路径
	private final static String REFUND_ROOT_DIR = "/refund";
	// 跳转到退款页面
	private final static String REFUND_LIST_PAGE = REFUND_ROOT_DIR + "/refundListPage";
	// 跳转到退款确认页面
	private final static String REFUND_PAGE = REFUND_ROOT_DIR + "/refundPage";

	/**
	 * 退票服务
	 */
	@Autowired
	private TribeRefundService tribeRefundService;

	/**
	 * 第一步：跳转到退票旅客和航班列表页面
	 */
	@RequestMapping("/goRefundListPage")
	public ModelAndView goRefundListPage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		return buidlModelAndView(REFUND_LIST_PAGE);
	}

	/**
	 * 第二步：查询退票旅客和航班列表数据
	 */
	@RequestMapping("/queryRefundFlightPassengerList")
	@ResponseBody
	public TPQueryOrderFlightAndPassengersCanRefundResp queryRefundFlightPassengerList(
			@RequestBody TPQueryOrderFlightAndPassengersCanRefundReq tpRefundReq, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		return tribeRefundService.queryRefundFlightPassengerList(tpRefundReq);
	}

	/**
	 * 第三步：提交退票操作，获取退票确认页面的数据
	 */
	@RequestMapping("/submitRefund")
	@ResponseBody
	public TPSubmitRefundPassengersAndFlightResp submitRefund(
			@RequestBody TPSubmitRefundPassengersAndFlightReq submitRefundReq, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		return tribeRefundService.submitRefund(submitRefundReq);
	}

	/**
	 * 第四步：跳转到支付确认页面
	 */
	@RequestMapping("/goRefundPage")
	public ModelAndView goRefundPage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		return buidlModelAndView(REFUND_PAGE);
	}

	/**
	 * 第五步：查询退票旅客和航班列表数据
	 */
	@RequestMapping("/refund")
	@ResponseBody
	public TPRefundOrderTicketResp refund(@RequestBody TPRefundOrderTicketReq tpRefundOrderTicketReq,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		return tribeRefundService.refund(tpRefundOrderTicketReq);
	}

}
