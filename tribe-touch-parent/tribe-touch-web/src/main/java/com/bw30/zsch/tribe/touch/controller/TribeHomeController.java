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
import com.bw.zsch.client.android.model.message.AlertDialogAdReq;
import com.bw.zsch.client.android.model.message.AlertDialogAdResp;
import com.bw.zsch.client.android.model.message.GetHomePageReq;
import com.bw.zsch.client.android.model.message.GetHomePageResp;
import com.bw.zsch.client.android.model.message.GetPriceCalendarReq;
import com.bw.zsch.client.android.model.message.GetPriceCalendarResp;
import com.bw30.zsch.tribe.touch.home.service.TribeHomeService;

/**
 * touch首页展示控制器
 * 
 * @author ShengHao
 *
 *         2017年8月2日 - 下午2:58:30
 */
@Controller
@RequestMapping(value = "/tribe/home")
public class TribeHomeController extends TribeBaseController {

	// 首页页面的根路径
	private final static String HOME_ROOT_DIR = "/home";
	// 首页页面路径
	private final static String HOME_PAGE_URL = HOME_ROOT_DIR + "/homePage.vm";
	// 价格日历页面路径
	private final static String PRICE_CANLENDER_PAGE_URL = HOME_ROOT_DIR + "/priceCanlender.vm";

	/**
	 * 首页tribe服务
	 */
	@Autowired
	private TribeHomeService tribeHomeService;

	/**
	 * 跳转到首页
	 */
	@RequestMapping("/goHomePage")
	public ModelAndView goHomePage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		return buidlModelAndView(HOME_PAGE_URL);
	}

	/**
	 * 跳转到价格日历页面
	 */
	@RequestMapping("/priceCanlender")
	public ModelAndView priceCanlender(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		return buidlModelAndView(PRICE_CANLENDER_PAGE_URL);
	}

	/**
	 * H5页面通过ajax获取首页资源数据，包括banner、公告等资源
	 */
	@RequestMapping("/queryHomeResourceInfo")
	@ResponseBody
	public GetHomePageResp queryHomeResourceInfo(@RequestBody GetHomePageReq getHomePageReq, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		return tribeHomeService.getHomePage(getHomePageReq);
	}

	/**
	 * H5页面通过ajax获取广告资源
	 */
	@RequestMapping("/queryAlertDialogAd")
	@ResponseBody
	public AlertDialogAdResp queryAlertDialogAd(@RequestBody AlertDialogAdReq alertDialogAdReq,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		return tribeHomeService.queryAlertDialogAd(alertDialogAdReq);
	}

	/**
	 * H5页面通过ajax获取价格日历数据
	 */
	@RequestMapping("/queryPriceCalendar")
	@ResponseBody
	public GetPriceCalendarResp queryPriceCalendar(@RequestBody GetPriceCalendarReq getPriceCalendarReq,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		return tribeHomeService.queryPriceCalendar(getPriceCalendarReq);
	}

}
