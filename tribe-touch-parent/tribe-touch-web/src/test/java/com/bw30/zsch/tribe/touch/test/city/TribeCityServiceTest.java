package com.bw30.zsch.tribe.touch.test.city;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bw.zsch.client.android.model.common.enumeration.CityType;
import com.bw.zsch.client.android.model.message.QueryAllCityReq;
import com.bw.zsch.client.android.model.message.QueryAllCityReqBody;
import com.bw.zsch.client.android.model.message.QueryAllCityReqHead;
import com.bw.zsch.client.android.model.message.QueryHotCityReq;
import com.bw.zsch.client.android.model.message.QueryHotCityReqBody;
import com.bw.zsch.client.android.model.message.QueryHotCityReqHead;
import com.bw.zsch.client.android.model.message.SearchCityReq;
import com.bw.zsch.client.android.model.message.SearchCityReqBody;
import com.bw.zsch.client.android.model.message.SearchCityReqHead;
import com.bw30.zsch.tribe.touch.city.service.TribeCityService;
import com.bw30.zsch.tribe.touch.test.base.BaseJunit4Test;

/**
 * 城市服务测试类
 * 
 * @author ShengHao
 *
 */
public class TribeCityServiceTest extends BaseJunit4Test {

	@Autowired
	private TribeCityService tribeCityService;

	@Test
	public void testSearchCity() throws Exception {
		SearchCityReq searchCityReq = new SearchCityReq();

		SearchCityReqHead head = new SearchCityReqHead();
		head.setPlatformId(TELEPHONE_PLATFORMID);
		searchCityReq.setHead(head);

		SearchCityReqBody body = new SearchCityReqBody();
		body.setSearchType("1");
		body.setKeyword("PEK");
		body.setCityType(CityType.DOMESTIC);
		searchCityReq.setBody(body);

		tribeCityService.searchCity(searchCityReq);
	}

	@Test
	public void test_GuoNei_QueryAllCity() throws Exception {

		QueryAllCityReq queryAllCityReq = new QueryAllCityReq();

		QueryAllCityReqHead head = new QueryAllCityReqHead();
		head.setPlatformId(TELEPHONE_PLATFORMID);
		queryAllCityReq.setHead(head);

		QueryAllCityReqBody body = new QueryAllCityReqBody();
		body.setCityType(CityType.DOMESTIC);
		queryAllCityReq.setBody(body);

		tribeCityService.queryAllCity(queryAllCityReq);

	}

	@Test
	public void test_GuoJi_QueryAllCity() throws Exception {

		QueryAllCityReq queryAllCityReq = new QueryAllCityReq();

		QueryAllCityReqHead head = new QueryAllCityReqHead();
		head.setPlatformId(TELEPHONE_PLATFORMID);
		queryAllCityReq.setHead(head);

		QueryAllCityReqBody body = new QueryAllCityReqBody();
		body.setCityType(CityType.INTERNATIONAL);
		queryAllCityReq.setBody(body);

		tribeCityService.queryAllCity(queryAllCityReq);

	}

	@Test
	public void test_GuoNei_queryHotCity() throws Exception {

		QueryHotCityReq queryHotCityReq = new QueryHotCityReq();
		QueryHotCityReqHead head = new QueryHotCityReqHead();
		head.setPlatformId(TELEPHONE_PLATFORMID);
		queryHotCityReq.setHead(head);
		QueryHotCityReqBody body = new QueryHotCityReqBody();
		body.setCityType(CityType.DOMESTIC);
		queryHotCityReq.setBody(body);
		tribeCityService.queryHotCity(queryHotCityReq);

	}

	@Test
	public void test_GuoJi_queryHotCity() throws Exception {

		QueryHotCityReq queryHotCityReq = new QueryHotCityReq();
		QueryHotCityReqHead head = new QueryHotCityReqHead();
		head.setPlatformId(TELEPHONE_PLATFORMID);
		queryHotCityReq.setHead(head);
		QueryHotCityReqBody body = new QueryHotCityReqBody();
		body.setCityType(CityType.INTERNATIONAL);
		queryHotCityReq.setBody(body);
		tribeCityService.queryHotCity(queryHotCityReq);

	}

}
