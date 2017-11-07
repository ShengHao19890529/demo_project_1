package com.bw30.zsch.tribe.touch.controller;

import static com.bw30.zsch.tribe.touch.constants.SystemConstants.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
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
import com.bw30.zsch.tribe.touch.changedate.service.bean.ClientTouchChangeDateRequestBean;
import com.bw30.zsch.tribe.touch.constants.SystemConstants;
import com.bw30.zsch.tribe.touch.constants.SystemResponseCodeEnum;
import com.bw30.zsch.tribe.touch.system.exception.ParamErrorException;
import com.bw30.zsch.tribe.touch.user.service.bean.LoginConstants;
import com.zsch.portal.model.pojo.Bw30RegisterUser;

/**
 * 改期控制器
 * 
 * @author ShengHao
 *
 *         2017年7月27日 - 上午9:28:11
 */
@Controller
@RequestMapping(value = "/tribe/changeDate")
public class TribeChangeDateController extends TribeBaseController {

	private final static Logger LOG = Logger.getLogger(TribeChangeDateController.class);

	// 改期页面的根路径
	private final static String CHANGE_DATE_ROOT_DIR = "/changeDate";
	// 查询可改期旅客列表的页面路径
	private final static String CHANGE_DATE_PASSENGER_PAGE_URL = CHANGE_DATE_ROOT_DIR + "/changeDatePassengerPage.vm";
	// 跳转到改期航班时间选择页面
	private final static String CHANGE_DATE_CHOOSE_PAGE_URL = CHANGE_DATE_ROOT_DIR + "/changeDateChoosePage.vm";
	// 跳转到改期航班列表页面
	private final static String CHANGE_DATE_FLIGHT_LIST_PAGE_URL = CHANGE_DATE_ROOT_DIR
			+ "/changeDateFlightListPage.vm";
	// 跳转到提交航班详情页面
	private final static String CHANGE_DATE_DETAIL_INFO_PAGE_URL = CHANGE_DATE_ROOT_DIR
			+ "/changeDateDetailInfoPage.vm";
	// 跳转到免费改期成功页面
	private final static String FREE_CHANGE_DATE_SUCCESS_PAGE_URL = CHANGE_DATE_ROOT_DIR + "/freeChangeDateSuccess.vm";

	/**
	 * 改期服务
	 */
	@Autowired
	private TribeChangeDateService tribeChangeDateService;

	/**
	 * 安卓或者IOS客户端浏览器跳转到可改期旅客列表页面<br>
	 * 该请求是给客户端使用的，适配因工期紧张无法在客户端完成改期操作，touch做适配<br>
	 * touch自己的改期也可以请求该地址
	 */
	@RequestMapping("/clientAccessTouchChangeDate")
	public ModelAndView clientAccessTouchChangeDate(@Valid ClientTouchChangeDateRequestBean requestBean,
			BindingResult validResult, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {

		// 参数非空性和合法性校验
		if (validResult.hasErrors()) {
			LOG.info("客户端接入touch改期请求，所传参数不合法，入参：" + JSON.toJSONString(requestBean) + " ， 参数错误信息："
					+ JSON.toJSONString(validResult.getAllErrors()));

			// 抛出自定义异常，供 GlobalExceptionHandler 做相应处理
			throw new ParamErrorException(SystemResponseCodeEnum.CLIENT_CHANGE_DATE_REQUEST_PARAM_ERROR.getMsg());
		}

		String telephoneNo = null;
		String loginType = (String) request.getSession().getAttribute(LoginConstants.LOGIN_TYPE);
		// 非会员登陆方式
		if (LoginConstants.LOGIN_TYPE_VALUE_TRIBE_GUEST.equals(loginType)) {
			Bw30RegisterUser loginUser = (Bw30RegisterUser) request.getSession()
					.getAttribute(SystemConstants.PORT_LOGIN_USER);
			telephoneNo = loginUser.getMobilephone();
			LOG.info("非会员登陆，手机号：" + telephoneNo);
		}

		modelMap.put(ORDER_ID, requestBean.getOrderId());
		modelMap.put(TELEPHONE_NO, telephoneNo);
		return buidlModelAndView(CHANGE_DATE_PASSENGER_PAGE_URL);

	}

	/**
	 * H5页面通过ajax获取可改期的旅客列表数据
	 */
	@RequestMapping("/queryChangeDatePassengerList")
	@ResponseBody
	public TPQueryOrderTicketCanChangeDateResp queryChangeDatePassengerList(
			@RequestBody TPQueryOrderTicketCanChangeDateReq changeDatePassengerReq, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		return tribeChangeDateService.queryChangeDatePassengerList(changeDatePassengerReq);
	}

	/**
	 * H5页面通过ajax提交需要改期的乘客和航班，做校验使用<br>
	 * 获取到的订单详情信息由H5做localstore存储，供后面的流程使用
	 */
	@RequestMapping("/submitCanChangePassengersFlight")
	@ResponseBody
	public TPSubmitCanChangePassengersAndFlightResp submitCanChangePassengersFlight(
			@RequestBody TPSubmitCanChangePassengersAndFlightReq submitPassengerReq, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		return tribeChangeDateService.submitCanChangePassengersFlight(submitPassengerReq);
	}

	/**
	 * 跳转到改期日期选择页面
	 */
	@RequestMapping("/goChangeDateChoosePage")
	public ModelAndView goChangeDateChoosePage(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		return buidlModelAndView(CHANGE_DATE_CHOOSE_PAGE_URL);
	}

	/**
	 * 跳转到改期航班列表页面
	 */
	@RequestMapping("/goChangeDateFlightListPage")
	public ModelAndView goChangeDateFlightListPage(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		return buidlModelAndView(CHANGE_DATE_FLIGHT_LIST_PAGE_URL);
	}

	/**
	 * H5页面通过ajax获取可改期的航班列表数据
	 */
	@RequestMapping("/queryChangeDateFlightList")
	@ResponseBody
	public TPQueryOrderFlightCanChangeDateResp queryChangeDateFlightList(
			@RequestBody TPQueryOrderFlightCanChangeDateReq changeDateFlightReq, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		return tribeChangeDateService.queryChangeDateFlightList(changeDateFlightReq);
	}

	/**
	 * H5页面通过ajax提交选择的改期航班
	 */
	@RequestMapping("/changeDate")
	@ResponseBody
	public TPChangeDateChargeResp changeDate(@RequestBody TPChangeDateChargeReq tpChangeDateChargeReq,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		return tribeChangeDateService.changeDate(tpChangeDateChargeReq);
	}

	/**
	 * 跳转到提交航班详情页面
	 */
	@RequestMapping("/goChangeDateDetailInfoPage")
	public ModelAndView goChangeDateDetailInfoPage(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		return buidlModelAndView(CHANGE_DATE_DETAIL_INFO_PAGE_URL);
	}

	/**
	 * 取消改期
	 */
	@RequestMapping("/cancelChangeDate")
	@ResponseBody
	public TPCancelChangeDateResp cancelChangeDate(@RequestBody TPCancelChangeDateReq tpCancelChangeDateReq,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		return tribeChangeDateService.cancelChangeDate(tpCancelChangeDateReq);
	}

	/**
	 * 免费改期
	 */
	@RequestMapping("/freeChangeDate")
	@ResponseBody
	public TPSubmitProcessFreeDateChangeResp freeChangeDate(
			@RequestBody TPSubmitProcessFreeDateChangeReq submitProcessFreeDateChangeReq, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		return tribeChangeDateService.freeChangeDate(submitProcessFreeDateChangeReq);
	}

	/**
	 * 跳转到提交航班详情页面
	 */
	@RequestMapping("/goFreeChangeSuccessPage")
	public ModelAndView goFreeChangeSuccessPage(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		return buidlModelAndView(FREE_CHANGE_DATE_SUCCESS_PAGE_URL);
	}

	/**
	 * 过滤航班列表
	 */
	@RequestMapping("/filterFlightList")
	@ResponseBody
	public TPFilterAndSortReshopSearchResp filterFlightList(
			@RequestBody TPFilterAndSortReshopSearchReq filterFlightListReq, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		return tribeChangeDateService.filterFlightList(filterFlightListReq);
	}

}
