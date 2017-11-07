package com.bw30.zsch.tribe.touch.home.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.bw.mall.client.service.client.dubbo.TheTribeClientService;
import com.bw.zsch.client.android.model.message.AlertDialogAdReq;
import com.bw.zsch.client.android.model.message.AlertDialogAdResp;
import com.bw.zsch.client.android.model.message.GetHomePageReq;
import com.bw.zsch.client.android.model.message.GetHomePageResp;
import com.bw.zsch.client.android.model.message.GetPriceCalendarReq;
import com.bw.zsch.client.android.model.message.GetPriceCalendarResp;
import com.bw30.zsch.tribe.touch.home.service.TribeHomeService;

/**
 * 首页tribe服务接口实现类
 * 
 * @author ShengHao
 *
 *         2017年8月2日 - 下午2:59:59
 */
@Service
public class TribeHomeServiceImpl implements TribeHomeService {

	private final static Logger LOG = Logger.getLogger(TribeHomeServiceImpl.class);

	/**
	 * 四川航空port提供的dubbo服务
	 */
	@Autowired
	private TheTribeClientService theTribeClientService;

	@Override
	public GetHomePageResp getHomePage(GetHomePageReq getHomePageReq) throws Exception {

		LOG.info("调用port首页数据获取接口 get_home_page ， 入参：" + JSON.toJSONString(getHomePageReq));
		GetHomePageResp getHomePageResp = theTribeClientService.get_home_page(getHomePageReq);
		LOG.info("调用port首页数据获取接口 get_home_page ， 响应：" + JSON.toJSONString(getHomePageResp));

		return getHomePageResp;
	}

	@Override
	public AlertDialogAdResp queryAlertDialogAd(AlertDialogAdReq alertDialogAdReq) throws Exception {

		LOG.info("调用port获取弹出广告数据接口 alert_dialog_ad ， 入参：" + JSON.toJSONString(alertDialogAdReq));
		AlertDialogAdResp alertDialogAdResp = theTribeClientService.alert_dialog_ad(alertDialogAdReq);
		LOG.info("调用port获取弹出广告数据接口 alert_dialog_ad ， 响应：" + JSON.toJSONString(alertDialogAdResp));

		return alertDialogAdResp;
	}

	@Override
	public GetPriceCalendarResp queryPriceCalendar(GetPriceCalendarReq getPriceCalendarReq) throws Exception {

		LOG.info("调用port获取价格日历接口 get_price_calendar ， 入参：" + JSON.toJSONString(getPriceCalendarReq));
		GetPriceCalendarResp getPriceCalendarResp = theTribeClientService.get_price_calendar(getPriceCalendarReq);
		LOG.info("调用port获取价格日历接口 get_price_calendar ，响应：" + JSON.toJSONString(getPriceCalendarResp));

		return getPriceCalendarResp;
	}

}
