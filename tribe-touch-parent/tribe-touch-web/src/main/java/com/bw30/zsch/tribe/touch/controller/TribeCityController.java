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

import com.bw.zsch.client.android.model.message.QueryAllCityReq;
import com.bw.zsch.client.android.model.message.QueryAllCityResp;
import com.bw.zsch.client.android.model.message.QueryHotCityReq;
import com.bw.zsch.client.android.model.message.QueryHotCityResp;
import com.bw.zsch.client.android.model.message.SearchCityReq;
import com.bw.zsch.client.android.model.message.SearchCityResp;
import com.bw30.zsch.tribe.touch.city.service.TribeCityService;

/**
 * 城市控制器
 * 
 * @author ShengHao
 *
 *         2017年8月15日 - 下午2:21:07
 */
@Controller
@RequestMapping(value = "/tribe/city")
public class TribeCityController extends TribeBaseController {

	// 城市页面的根路径
	private final static String CITY_ROOT_DIR = "/city";
	// 城市列表页面路径
	private final static String CITY_LIST_PAGE_URL = CITY_ROOT_DIR + "/cityList.vm";

	/**
	 * 城市服务
	 */
	@Autowired
	private TribeCityService tribeCityService;

	/**
	 * 跳转到航班列表展示页面
	 */
	@RequestMapping("/goCityListPage")
	public ModelAndView goCityListPage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		return buidlModelAndView(CITY_LIST_PAGE_URL);
	}

	/**
	 * H5页面通过ajax获取所有的城市列表数据
	 */
	@RequestMapping("/queryAllCity")
	@ResponseBody
	public QueryAllCityResp queryAllCity(@RequestBody QueryAllCityReq queryAllCityReq, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		return tribeCityService.queryAllCity(queryAllCityReq);
	}

	/**
	 * H5页面通过ajax获取热门城市列表数据
	 */
	@RequestMapping("/queryHotCity")
	@ResponseBody
	public QueryHotCityResp queryHotCity(@RequestBody QueryHotCityReq queryHotCityReq, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		return tribeCityService.queryHotCity(queryHotCityReq);
	}

	/**
	 * H5页面通过ajax根据城市三字码查询城市
	 */
	@RequestMapping("/queryCity")
	@ResponseBody
	public SearchCityResp queryCity(@RequestBody SearchCityReq searchCityReq, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		return tribeCityService.searchCity(searchCityReq);
	}

}
