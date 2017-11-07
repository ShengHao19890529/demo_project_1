package com.bw30.zsch.tribe.touch.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.bw.zsch.client.android.model.trip.order.TPQueryOrderDetailReq;
import com.bw.zsch.client.android.model.trip.order.TPQueryOrderDetailResp;
import com.bw.zsch.client.android.model.trip.order.TPQueryOrderReq;
import com.bw.zsch.client.android.model.trip.order.TPQueryOrderResp;
import com.bw30.zsch.tribe.touch.order.service.TribeOrderService;

/**
 * 订单控制器
 * 
 * @author ShengHao
 *
 *         2017年7月27日 - 上午9:18:14
 */
@Controller
@RequestMapping(value = "/tribe/order")
public class TribeOrderController extends TribeBaseController {

	// 订单页面的根路径
	private final static String ORDER_ROOT_DIR = "/order";
	// 跳转到订单列表页面
	private final static String GO_ORDER_LIST_PAGE = ORDER_ROOT_DIR + "/goOrderListPage";
	// 跳转到订单详情页面
	private final static String GO_ORDER_DETAIL_PAGE = ORDER_ROOT_DIR + "/goOrderDetailPage";

	/**
	 * 订单服务
	 */
	@Autowired
	private TribeOrderService tribeOrderService;

	/**
	 * 跳转到订单列表页面
	 */
	@RequestMapping("/goOrderListPage")
	public ModelAndView goOrderListPage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		return buidlModelAndView(GO_ORDER_LIST_PAGE);
	}

	/**
	 * 查询订单列表数据
	 */
	@RequestMapping("/queryOrderList")
	@ResponseBody
	public TPQueryOrderResp queryOrderList(TPQueryOrderReq tpQueryOrderReq, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		return tribeOrderService.queryOrderList(tpQueryOrderReq);
	}

	/**
	 * 跳转到订单详情页面
	 */
	@RequestMapping("/goOrderDetailPage")
	public ModelAndView goOrderDetailPage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		return buidlModelAndView(GO_ORDER_DETAIL_PAGE);
	}

	/**
	 * 查询订单详情数据
	 */
	@RequestMapping("/queryOrderDetail")
	@ResponseBody
	public TPQueryOrderDetailResp queryOrderDetail(TPQueryOrderDetailReq tpQueryOrderDetailReq,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		return tribeOrderService.queryOrderDetail(tpQueryOrderDetailReq);
	}

}
