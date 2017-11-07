package com.bw30.zsch.tribe.touch.test.home;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bw.zsch.client.android.model.common.pojo.City;
import com.bw.zsch.client.android.model.message.GetHomePageReq;
import com.bw.zsch.client.android.model.message.GetHomePageReqBody;
import com.bw.zsch.client.android.model.message.GetHomePageReqHead;
import com.bw.zsch.client.android.model.message.GetPriceCalendarReq;
import com.bw.zsch.client.android.model.message.GetPriceCalendarReqBody;
import com.bw.zsch.client.android.model.message.GetPriceCalendarReqHead;
import com.bw30.zsch.tribe.touch.home.service.TribeHomeService;
import com.bw30.zsch.tribe.touch.test.base.BaseJunit4Test;

/**
 * 首页测试
 * 
 * @author ShengHao
 *
 *         2017年10月11日 - 上午10:50:13
 */
public class TribeHomeServiceTest extends BaseJunit4Test {

	@Autowired
	private TribeHomeService tribeHomeService;

	@Test
	public void test_getHomePage() throws Exception {
		GetHomePageReq getHomePageReq = new GetHomePageReq();

		GetHomePageReqHead head = new GetHomePageReqHead();
		head.setPlatformId(TELEPHONE_PLATFORMID);
		getHomePageReq.setHead(head);

		GetHomePageReqBody body = new GetHomePageReqBody();
		getHomePageReq.setBody(body);

		tribeHomeService.getHomePage(getHomePageReq);

	}

	@Test
	public void test_queryPriceCalendar() throws Exception {
		GetPriceCalendarReq getPriceCalendarReq = new GetPriceCalendarReq();

		GetPriceCalendarReqHead head = new GetPriceCalendarReqHead();
		head.setPlatformId(TELEPHONE_PLATFORMID);
		getPriceCalendarReq.setHead(head);

		GetPriceCalendarReqBody body = new GetPriceCalendarReqBody();
		body.setArriveCity(buildCity("PEK", "北京"));
		body.setTakeOffCity(buildCity("CTU", "成都"));
		getPriceCalendarReq.setBody(body);

		tribeHomeService.queryPriceCalendar(getPriceCalendarReq);
	}

	private City buildCity(String code, String name) {
		City city = new City();
		city.setCode(code);
		city.setName(name);
		return city;
	}

}
