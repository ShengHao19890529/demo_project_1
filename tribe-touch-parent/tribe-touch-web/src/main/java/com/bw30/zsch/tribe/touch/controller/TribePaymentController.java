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
import com.bw.zsch.client.android.model.trip.pay.TPGetPayChannelInfoReq;
import com.bw.zsch.client.android.model.trip.pay.TPGetPayChannelInfoResp;
import com.bw.zsch.client.android.model.trip.pay.TPPaymentReq;
import com.bw.zsch.client.android.model.trip.pay.TPPaymentResp;
import com.bw.zsch.client.android.model.trip.pay.TPSubmitPayReq;
import com.bw.zsch.client.android.model.trip.pay.TPSubmitPayResp;
import com.bw30.zsch.tribe.touch.payment.service.TribePaymentService;

/**
 * 支付控制器
 * 
 * @author ShengHao
 *
 *         2017年7月27日 - 上午9:19:05
 */
@Controller
@RequestMapping(value = "/tribe/payment")
public class TribePaymentController extends TribeBaseController {

	// 支付页面的根路径
	private final static String PAYMENT_ROOT_DIR = "/payment";
	// 跳转到支付列表页面
	private final static String PAYMENT_LIST_PAGE = PAYMENT_ROOT_DIR + "/paymentListPage";

	/**
	 * 支付服务
	 */
	@Autowired
	private TribePaymentService tribePaymentService;

	/**
	 * 提交支付，应该是做支付验证使用，不调用也能过，最好还是调用下
	 */
	@RequestMapping("/submitPay")
	@ResponseBody
	public TPSubmitPayResp submitPay(@RequestBody TPSubmitPayReq tpSubmitPayReq, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		return tribePaymentService.submitPay(tpSubmitPayReq);
	}

	/**
	 * 跳转到支付列表页面
	 */
	@RequestMapping("/goPaymentListPage")
	public ModelAndView goPaymentListPage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		return buidlModelAndView(PAYMENT_LIST_PAGE);
	}

	/**
	 * 查询支付列表
	 */
	@RequestMapping("/queryPayInfoList")
	@ResponseBody
	public TPGetPayChannelInfoResp queryPayInfoList(@RequestBody TPGetPayChannelInfoReq tpGetPayChannelInfoReq,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		return tribePaymentService.queryPayInfoList(tpGetPayChannelInfoReq);
	}

	/**
	 * 订单支付
	 */
	@RequestMapping("/payment")
	@ResponseBody
	public TPPaymentResp payment(@RequestBody TPPaymentReq tpPaymentReq, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		return tribePaymentService.payment(tpPaymentReq);
	}

}
