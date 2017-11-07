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
 * 航班控制器
 * 
 * @author ShengHao
 *
 *         2017年7月21日 - 下午5:19:53
 */
@Controller
@RequestMapping(value = "/tribe/flight")
public class TribeFlightController extends TribeBaseController {

	// 航班页面的根路径
	private final static String FLIGHT_LIST_ROOT_DIR = "/flight";
	// 航班列表页面路径
	private final static String FLIGHT_LIST_PAGE_URL = FLIGHT_LIST_ROOT_DIR + "/flightList.vm";

	/**
	 * 航班服务
	 */
	@Autowired
	private TribeFlightService tribeFlightService;

	/**
	 * 跳转到航班列表展示页面
	 */
	@RequestMapping("/goFlightListPage")
	public ModelAndView goFlightListPage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		return buidlModelAndView(FLIGHT_LIST_PAGE_URL);
	}

	/**
	 * H5页面通过ajax获取航班列表的数据
	 */
	@RequestMapping("/queryFlightList")
	@ResponseBody
	public TPQueryFlightResp queryFlightList(@RequestBody TPQueryFlightReq tpQueryFlightReq, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		return tribeFlightService.queryFlightList(tpQueryFlightReq);
	}

	/**
	 * 过滤和排序航班列表
	 */
	@RequestMapping("/filterAndSortFlight")
	@ResponseBody
	public TPFilterAndSortFlightResp filterAndSortFlight(@RequestBody TPFilterAndSortFlightReq filterAndSortFlightReq,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		return tribeFlightService.filterAndSortFlight(filterAndSortFlightReq);
	}

	/**
	 * 点击"订票"按钮，将航班舱位信息添加到购物车里
	 */
	@RequestMapping("/addShoppingcart")
	@ResponseBody
	public TPAddShoppingCartResp addShoppingcart(@RequestBody TPAddShoppingCartReq tpAddShoppingCartReq,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		return tribeFlightService.addShoppingcart(tpAddShoppingCartReq);
	}

	/**
	 * 提供信息确认页面展示的数据
	 */
	@RequestMapping("/checkoutShoppingcart")
	@ResponseBody
	public TPCheckoutShoppingCartToPassengerDetailsResp checkoutShoppingcart(
			@RequestBody TPCheckoutShoppingCartToPassengerDetailsReq retriveShoppingCartReq, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		return tribeFlightService.checkoutShoppingcart(retriveShoppingCartReq);
	}

	/**
	 * 创建订单，提交乘机人信息
	 */
	@RequestMapping("/submitOrder")
	@ResponseBody
	public TPSubmitPassengerDetailsResp submitOrder(@RequestBody TPSubmitPassengerDetailsReq submitOrderReq,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		return tribeFlightService.submitOrder(submitOrderReq);
	}

	/**
	 * 返回xml数据测试，这里的mime可以自定义，用于convertor中定制化使用
	 */
	// @RequestMapping(value = "/myXmlTest", produces =
	// "application/xml;charset=UTF-8")
	// @ResponseBody
	// public JSONObject myXmlTest(HttpServletRequest request,
	// HttpServletResponse response, ModelMap modelMap)
	// throws Exception {
	// JSONObject jsonObject = new JSONObject();
	// jsonObject.put("username", "盛好");
	// jsonObject.put("password", "123456");
	// jsonObject.put("age", "28");
	// jsonObject.put("gender", "male");
	// return jsonObject;
	// }

}
