package com.bw30.zsch.tribe.touch.home.service;

import com.bw.zsch.client.android.model.message.AlertDialogAdReq;
import com.bw.zsch.client.android.model.message.AlertDialogAdResp;
import com.bw.zsch.client.android.model.message.GetHomePageReq;
import com.bw.zsch.client.android.model.message.GetHomePageResp;
import com.bw.zsch.client.android.model.message.GetPriceCalendarReq;
import com.bw.zsch.client.android.model.message.GetPriceCalendarResp;

/**
 * 首页tribe服务接口
 * 
 * @author ShengHao
 *
 *         2017年8月2日 - 下午2:59:59
 */
public interface TribeHomeService {

	/**
	 * 获取首页banner和公告等资源数据
	 */
	public GetHomePageResp getHomePage(GetHomePageReq getHomePageReq) throws Exception;

	/**
	 * 获取广告资源数据
	 */
	public AlertDialogAdResp queryAlertDialogAd(AlertDialogAdReq alertDialogAdReq) throws Exception;

	/**
	 * 获取价格日历
	 */
	public GetPriceCalendarResp queryPriceCalendar(GetPriceCalendarReq getPriceCalendarReq) throws Exception;

}
