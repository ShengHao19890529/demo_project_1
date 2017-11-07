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
import com.bw.zsch.client.android.model.message.CreateCommonPassengerReq;
import com.bw.zsch.client.android.model.message.CreateCommonPassengerResp;
import com.bw.zsch.client.android.model.message.DeleteCommonPassengerReq;
import com.bw.zsch.client.android.model.message.DeleteCommonPassengerResp;
import com.bw.zsch.client.android.model.message.QueryCommonPassengerReq;
import com.bw.zsch.client.android.model.message.QueryCommonPassengerResp;
import com.bw.zsch.client.android.model.message.UpdateCommonPassengerReq;
import com.bw.zsch.client.android.model.message.UpdateCommonPassengerResp;
import com.bw30.zsch.tribe.touch.passenger.service.TribePassengerService;

/**
 * 乘机人控制器
 * 
 * @author ShengHao
 *
 *         2017年8月4日 - 上午11:30:54
 */
@Controller
@RequestMapping(value = "/tribe/passenger")
public class TribePassengerController extends TribeBaseController {

	// 乘客页面的根路径
	private final static String PASSENGER_ROOT_DIR = "/passenger";
	// 乘客添加页面
	private final static String PASSENGER_ADD_PAGE_URL = PASSENGER_ROOT_DIR + "/passengerAddPage.vm";

	/**
	 * 乘客服务
	 */
	@Autowired
	private TribePassengerService tribePassengerService;

	/**
	 * 跳转到乘客添加页面
	 */
	@RequestMapping("/goPassengerAddPage")
	public ModelAndView goPassengerAddPage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		return buidlModelAndView(PASSENGER_ADD_PAGE_URL);
	}

	/**
	 * H5页面通过ajax添加乘客
	 */
	@RequestMapping("/createCommonPassenger")
	@ResponseBody
	public CreateCommonPassengerResp createCommonPassenger(@RequestBody CreateCommonPassengerReq createPassengerReq,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		return tribePassengerService.createCommonPassenger(createPassengerReq);
	}

	/**
	 * H5页面通过ajax删除乘客
	 */
	@RequestMapping("/deleteCommonPassenger")
	@ResponseBody
	public DeleteCommonPassengerResp deleteCommonPassenger(@RequestBody DeleteCommonPassengerReq deletePassengerReq,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		return tribePassengerService.deleteCommonPassenger(deletePassengerReq);
	}

	/**
	 * H5页面通过ajax更新乘客信息
	 */
	@RequestMapping("/updateCommonPassenger")
	@ResponseBody
	public UpdateCommonPassengerResp updateCommonPassenger(@RequestBody UpdateCommonPassengerReq updatePassengerReq,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		return tribePassengerService.updateCommonPassenger(updatePassengerReq);
	}

	/**
	 * H5页面通过ajax查询乘客列表信息
	 */
	@RequestMapping("/queryCommonPassenger")
	@ResponseBody
	public QueryCommonPassengerResp queryCommonPassenger(@RequestBody QueryCommonPassengerReq queryPassengerReq,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		return tribePassengerService.queryCommonPassenger(queryPassengerReq);
	}

}
